package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropClosedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;

@SuppressWarnings("all")
@Service
@Validated
@Transactional
public class EavropBookingServiceImpl implements EavropBookingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
 
    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropBookingServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}
	
	/**
	 * 
	 */
	@Override
	public void createBooking(CreateBookingCommand aCommand){
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		
		//Create booking
		Booking booking = new Booking(aCommand.getBookingType(), aCommand.getBookingStartDateTime(), aCommand.getBookingEndDateTime(), aCommand.getAdditionalService(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getUseInterpreter());
		//Add booking to eavrop
		eavrop.addBooking(booking);

        if(log.isDebugEnabled()){
        	log.debug(String.format("booking created :: %s", booking.toString()));
        }
		//handle the booking
		handleBookingAdded(eavrop.getEavropId(), eavrop.getArendeId(), booking);
	}
	
	/**
	 * 
	 */
	@Override
	public void changeBookingStatus(ChangeBookingStatusCommand aCommand){
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		BookingStatusType bookingStatus = aCommand.getBookingStatus();
		
		Note deviationNote = createDeviationNote(aCommand.getComment(), aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
		eavrop.setBookingStatus(aCommand.getBookingId(), aCommand.getBookingStatus(),deviationNote);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Status changed for booking:: %s", aCommand.getBookingId().toString()));
        }
		
		if(bookingStatus.isDeviant()){
			handleBookingDeviation(eavrop.getEavropId(), eavrop.getArendeId(), eavrop.getUtredningType(), eavrop.getBooking(aCommand.getBookingId()));
		}
	}

	/**
	 * 
	 */
	@Override
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand aCommand){
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		InterpreterBookingStatusType interpreterBookingStatus = aCommand.getInterpreterbookingStatus();
		
		Note deviationNote = createDeviationNote(aCommand.getComment(), aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
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
	 */
	@Override
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand ){
		//Look up eavrop 
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		BookingDeviationResponse bookingDeviationResponse = createBookingDeviationResponse(aCommand);
		
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
	
	private Note createDeviationNote(String text, HsaId hsaId, String name, String role, String organisation, String unit){
		if(StringUtils.isBlankOrNull(text)){
			return null;
		}
		HoSPerson person = null;
		if(!StringUtils.isBlankOrNull(name) || hsaId != null){
			person  = new HoSPerson(hsaId, name, role, organisation, unit);
		}

		return new Note(NoteType.BOOKING_DEVIATION, text, person);	
	}

//	private BookingDeviationResponse  createBookingDeviationResponse(String responseType, DateTime responseTimestamp, String personName, String personRole, String personOrganistation, String personUnit, String personPhone, String personEmail, String comment) {
//		
//		BookingDeviationResponseType bookingDeviationResponseType = BookingDeviationResponseType.valueOf(responseType);
//		
//		Person person = createBestallaradministrator(personName, personRole, personOrganistation, personUnit, personPhone, personEmail);
//		Note note = createResponseNote(comment, person);
//
//		return new BookingDeviationResponse(bookingDeviationResponseType, responseTimestamp, person, note);
//
//	}

	private BookingDeviationResponse  createBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand ) {
		return new BookingDeviationResponse(aCommand.getResponseType(), aCommand.getResponseTimestamp(), aCommand.getBestallaradministrator(), createResponseNote(aCommand.getResponseComment(), aCommand.getBestallaradministrator()));
	}

	
	private Bestallaradministrator createBestallaradministrator(String personName, String personRole, String personOrganistation, String personUnit, String personPhone, String personEmail){
		return new Bestallaradministrator(personName,  personRole, personOrganistation, personUnit, personPhone, personEmail);
	}

	private Note createResponseNote(String comment, Person person) {
		Note note = null;
		
		if(!StringUtils.isBlankOrNull(comment)){
			note = new Note(NoteType.BOOKING_DEVIATION_RESPONSE, comment, person);
		}
		
		return note;
	}

	private Eavrop getEavropByArendeId(ArendeId arendeId){
		Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop with ArendeId %s not found", arendeId.toString()));
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

	private DomainEventPublisher getDomainEventPublisher(){
		return this.domainEventPublisher;
	}
	
	//Event handling methods
	private void handleBookingAdded(EavropId eavropId, ArendeId arendeId, Booking booking){
		BookingId bookingId = booking.getBookingId();
		BookingType bookingType = booking.getBookingType();
		DateTime bookingStart = booking.getStartDateTime();
		DateTime bookingEnd = booking.getEndDateTime();
		String resourceName = (booking.getBookingResource()!=null)?booking.getBookingResource().getName():null;
		String resourceRole = (booking.getBookingResource()!=null)?booking.getBookingResource().getRole():null;
		boolean isInterpreter = (booking.getInterpreterBooking()!=null)?Boolean.TRUE:Boolean.FALSE;
		
		BookingCreatedEvent event = new BookingCreatedEvent(eavropId, arendeId, bookingId, bookingType, 
				bookingStart, bookingEnd, resourceName, resourceRole,  isInterpreter);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingCreatedEvent created :: %s", event.toString()));
        }

		getDomainEventPublisher().post(event);
	}

	private void handleBookingDeviation(EavropId eavropId, ArendeId arendeId, UtredningType utredningType, Booking booking){
		if(booking == null){
			return;
		}
		BookingId bookingId = booking.getBookingId();
		BookingDeviationType bookingDeviationType = BookingDeviationType.convert(booking.getBookingStatus());
		Note deviationNote = booking.getDeviationNote();
		
		boolean isReasonForOnhold = BookingDeviationTypeUtil.isBookingStatusReasonForOnHold(booking.getBookingStatus(), utredningType);

		BookingDeviationEvent event = new BookingDeviationEvent(eavropId, arendeId, bookingId, bookingDeviationType, isReasonForOnhold, deviationNote);
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
