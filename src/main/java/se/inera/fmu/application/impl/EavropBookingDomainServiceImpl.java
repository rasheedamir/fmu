package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropBookingDomainService;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropClosedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;

@SuppressWarnings("ALL")
@Service
@Validated
@Transactional
public class EavropBookingDomainServiceImpl implements EavropBookingDomainService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropBookingDomainServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}
	
	private DomainEventPublisher getDomainEventPublisher(){
		return this.domainEventPublisher;
	}
	
	
	/**
	 * 
	 */
	public void createBooking(CreateBookingCommand aCommand){
		validateCreateBookingCommand(aCommand);
		
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		
		Person hosPerson = createHosPerson(aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
		//Create booking
		Booking booking = new Booking(aCommand.getBookingType(), aCommand.getBookingStartDateTime(), aCommand.getBookingEndDateTime(), hosPerson, aCommand.isUseInterpreter());
		//Add booking to eavrop
		eavrop.addBooking(booking);

        if(log.isDebugEnabled()){
        	log.debug(String.format("booking created :: %s", booking.toString()));
        }
		//handle the booking
		handleBookingAdded(eavrop.getEavropId(), booking.getBookingId());
		
	}
	
	private void validateCreateBookingCommand(CreateBookingCommand command){
		Validate.notNull(command.getEavropId());
		Validate.notNull(command.getBookingType());
		Validate.notNull(command.getBookingStartDateTime());
		Validate.notNull(command.getBookingEndDateTime());
	}
	
	private HoSPerson createHosPerson(String name, String role, String organisation, String unit){
		if(name == null){
			return null;
		}
		return new HoSPerson(name, role, organisation, unit);	
	}

	private Note createDeviationNote(String text, String name, String role, String organisation, String unit){
		if(text == null){
			return null;
		}
		HoSPerson person = createHosPerson(name, role, organisation, unit);
		
		return new Note(NoteType.BOOKING_DEVIATION, text, person);	
	}

	
	/**
	 * 
	 */
	public void changeBookingStatus(ChangeBookingStatusCommand aCommand){
		validateChangeBookingStatusCommand(aCommand);
		
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		BookingStatusType bookingStatus =aCommand.getBookingStatus();
		
		Note deviationNote = createDeviationNote(aCommand.getText(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
		eavrop.setBookingStatus(aCommand.getBookingId(), aCommand.getBookingStatus(),deviationNote);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Status changed for booking:: %s", aCommand.getBookingId().toString()));
        }
		
		if(bookingStatus.isCancelled()){
			handleBookingDeviation(eavrop.getEavropId(), aCommand.getBookingId());
		}
	}
	
	private void validateChangeBookingStatusCommand(ChangeBookingStatusCommand command){
		Validate.notNull(command.getEavropId());
		Validate.notNull(command.getBookingId());
		Validate.notNull(command.getBookingStatus());
	}
	
	private void validateChangeInterpreterBookingStatusCommand(ChangeInterpreterBookingStatusCommand command) {
		Validate.notNull(command.getEavropId());
		Validate.notNull(command.getBookingId());
		Validate.notNull(command.getInterpreterbookingStatus());
	}
	
	private void validateAddBookingDeviationResponseCommand(AddBookingDeviationResponseCommand command) {
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getBookingId());
		Validate.notNull(command.getResponse());
		Validate.notNull(command.getResponseTimestamp());
	}

	
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand aCommand){
		validateChangeInterpreterBookingStatusCommand(aCommand);
		
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		InterpreterBookingStatusType interpreterBookingStatus = aCommand.getInterpreterbookingStatus();
		
		Note deviationNote = createDeviationNote(aCommand.getText(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
		eavrop.setInterpreterBookingStatus(aCommand.getBookingId(), interpreterBookingStatus, deviationNote);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Status changed for interpreter booking:: %s", aCommand.getBookingId().toString()));
        }

		if(interpreterBookingStatus.isDeviant()){
			handleInterpreterBookingDeviation(eavrop.getEavropId(), aCommand.getBookingId());
		}
	}

	/**
	 * 
	 * @param arendeId
	 * @param bookingId
	 * @param bookingDeviationResponse
	 */
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand ){
		validateAddBookingDeviationResponseCommand(aCommand);
		
		//Look up eavrop and booking
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
	
		BookingDeviationResponse bookingDeviationResponse = createBookingDeviationResponse(aCommand.getResponse(), aCommand.getResponseTimestamp(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail(), aCommand.getResponseComment());
		
		eavrop.addBookingDeviationResponse(aCommand.getBookingId(), bookingDeviationResponse);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Booking deviation added for booking:: %s", aCommand.getBookingId().toString()));
        }
		
		//Depending on the response type from the bestallare, the eavrop is either restarted or stopped
		BookingDeviationResponseType responseType = bookingDeviationResponse.getResponseType();
		if(BookingDeviationResponseType.RESTART.equals(responseType)){
			handleEavropRestarted(eavrop.getEavropId());
		
		}else if(BookingDeviationResponseType.STOP.equals(responseType)) {
			handleEavropClosedByBestallare(eavrop.getEavropId());	
		}

	}

	private BookingDeviationResponse  createBookingDeviationResponse(String responseType, DateTime responseTimestamp, String personName, String personRole, String personOrganistation, String personUnit, String personPhone, String personEmail, String comment) {
		
		BookingDeviationResponseType bookingDeviationResponseType = BookingDeviationResponseType.valueOf(responseType);
		
		Person person = createPerson(personName, personRole, personOrganistation, personUnit, personPhone, personEmail);
		
		Note note = createNote(comment, person);

		return new BookingDeviationResponse(bookingDeviationResponseType, responseTimestamp, person, note);

	}
	
	private Person createPerson(String personName, String personRole, String personOrganistation, String personUnit, String personPhone, String personEmail){
		
		return new Bestallaradministrator(personName,  personRole, personOrganistation, personUnit, personPhone, personEmail);
		
	}
	
	private Note createNote(String comment, Person person) {
		Note note = null;
		
		if(!StringUtils.isBlankOrNull(comment)){
			note = new Note(NoteType.BOOKING_DEVIATION_RESPONSE, comment, person);
		}
		
		return note;
	}

	private Eavrop getEavropByArendeId(ArendeId arendeId){
		Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new IllegalArgumentException("Eavrop with arendeid:" + arendeId.toString() + " does not exist");
		}
		return eavrop;
	}

	private Eavrop getEavropByEavropId(EavropId eavropId){
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}
	
	private Booking getBookingById(Eavrop eavrop, BookingId bookingId){
		Booking booking = eavrop.getBooking(bookingId);
		if(booking==null){
			throw new IllegalArgumentException("Booking with id:" + bookingId.getId() + " is not present on Eavrop with EavropId: " + eavrop.getEavropId().toString());
		}
		return booking;
	}

	//Event handling methods
	private void handleBookingAdded(EavropId eavropId, BookingId bookingId){
		BookingCreatedEvent event = new BookingCreatedEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingCreatedEvent created :: %s", event.toString()));
        }

		getDomainEventPublisher().post(event);
	}

	private void handleBookingDeviation(EavropId eavropId, BookingId bookingId){
		BookingDeviationEvent event = new BookingDeviationEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingDeviationEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleInterpreterBookingDeviation(EavropId eavropId, BookingId bookingId){
		InterpreterBookingDeviationEvent event = new InterpreterBookingDeviationEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("InterpreterBookingDeviationEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleEavropRestarted(EavropId eavropId){
		EavropRestartedByBestallareEvent event = new EavropRestartedByBestallareEvent(eavropId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropRestartedByBestallareEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleEavropClosedByBestallare(EavropId eavropId){
		EavropClosedByBestallareEvent event = new EavropClosedByBestallareEvent(eavropId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropClosedByBestallareEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
}
