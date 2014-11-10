package se.inera.fmu.application.impl;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.google.common.eventbus.AsyncEventBus;

import se.inera.fmu.application.EavropBookingDomainService;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.AcceptedEavropState;
import se.inera.fmu.domain.model.eavrop.ApprovedEavropState;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropClosedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.OnHoldEavropState;
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
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.Person;

@SuppressWarnings("ALL")
@Service
@Validated
@Transactional
public class EavropBookingDomainServiceImpl implements EavropBookingDomainService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final AsyncEventBus eventBus;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param eventBus
     */
	@Inject
	public EavropBookingDomainServiceImpl(EavropRepository eavropRepository, AsyncEventBus eventBus) {
		this.eavropRepository = eavropRepository;
		this.eventBus = eventBus;
	}
	
	private AsyncEventBus getEventBus(){
		return this.eventBus;
	}
	
	
	/**
	 * 
	 */
	public void createBooking(EavropId eavropId, BookingType bookingType, DateTime startDateTime, DateTime endDateTime, Person person, boolean useInterpreter ){
		Eavrop eavrop = getEavropByEavropId(eavropId);
		
		
		//Create booking
		Booking booking = new Booking(bookingType, startDateTime, endDateTime, person, useInterpreter);
        if(log.isDebugEnabled()){
        	log.debug(String.format("booking created :: %s", booking.toString()));
        }
		//Add booking to eavrop
		eavrop.addBooking(booking);
		//handle the booking
		handleBookingAdded(eavrop.getEavropId(), booking.getBookingId());
		
	}
	
	/**
	 * 
	 */
	public void changeBookingStatus(EavropId eavropId, BookingId bookingId, BookingStatusType bookingStatus){
		this.changeBookingStatus(eavropId, bookingId, bookingStatus, null);
	}
	
	/**
	 * 
	 */
	public void changeBookingStatus(EavropId eavropId, BookingId bookingId, BookingStatusType bookingStatus, Note cancellationNote){
		Eavrop eavrop = getEavropByEavropId(eavropId);
		Booking booking =  getBookingById(eavrop, bookingId);
		
		booking.setBookingStatus(bookingStatus);
		booking.setDeviationNote(cancellationNote);
		
		//s
		if(bookingStatus.isCancelled()){
			if(BookingDeviationTypeUtil.isBookingStatusReasonForOnHold(booking.getBookingStatus(), eavrop.getUtredningType())){
				//State transition ACCEPTED -> ON_HOLD
				
				//TODO:H00RHG
				//eavrop.setEavropState(new OnHoldEavropState());
			}
			handleBookingDeviation(eavrop.getEavropId(), booking.getBookingId());
		}
	}
	
	/**
	 * 
	 */
	public void changeInterpreterBookingStatus(EavropId eavropId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus){
		this.changeInterpreterBookingStatus(eavropId, bookingId, interpreterStatus, null);
	}
		
	public void changeInterpreterBookingStatus(EavropId eavropId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote){
		Eavrop eavrop = getEavropByEavropId(eavropId);
		Booking booking =  getBookingById(eavrop, bookingId);
		
		InterpreterBooking interpreterBooking =  booking.getInterpreterBooking();
		if(interpreterBooking == null){
			//TODO: Implemement exception handling
			//throw EavropInterpreterBookingNotFoundException();
		}
		
		interpreterBooking.setInterpreterBookingStatus(interpreterStatus);
		interpreterBooking.setDeviationNote(cancellationNote);
		
		if(interpreterStatus.isDeviant()){
			handleInterpreterBookingDeviation(eavrop.getEavropId(), bookingId);
		}
		
	}

	/**
	 * 
	 * @param arendeId
	 * @param bookingId
	 * @param bookingDeviationResponse
	 */
	public void addBookingDeviationResponse(ArendeId arendeId, 
											BookingId bookingId, 
											String response, 
											DateTime responseTimestamp, 
											String personName, 
											String personRole, 
											String personOrganistation, 
											String personUnit, 
											String personPhone, 
											String personEmail, 
											String deviationResponseComment){
		//Look up eavrop and booking
		Eavrop eavrop = getEavropByArendeId(arendeId);
		Booking booking =  getBookingById(eavrop, bookingId);
		
		
		//Check booking status. maybe the state on the eavrop is enough, only eavrop with onhold state will accept bookingDeviationResponse  
		if( ! booking.getBookingStatus().isCancelled()){
			//TODO: create separate state machine for bookings
			throw new IllegalArgumentException("Booking with id:" + bookingId.getId() + " on eavrop with arendeId: " + eavrop.getArendeId().toString()+" does not have a cancelled status: " + booking.getBookingStatus());
		}
	
		BookingDeviationResponse bookingDeviationResponse = createBookingDeviationResponse(response, responseTimestamp, personName, personRole, personOrganistation, personUnit, personPhone, personEmail, deviationResponseComment);
		
		booking.setBookingDeviationResponse(bookingDeviationResponse);
		
		//Depending on the response type from the bestallare, the eavrop is either restarted or stopped
		BookingDeviationResponseType responseType = bookingDeviationResponse.getResponseType();
		if(BookingDeviationResponseType.RESTART.equals(responseType)){
			//TODO: SET new base date, use some kind of domain service
			//TODO:H00RHG
			//eavrop.setStartDate(bookingDeviationResponse.getResponseTimestamp().toLocalDate());
			//TODO:If startdate is reset we might need get new Eavrop properties since the number of assessment days might have changed depending on the start date
			//eavrop.setEavropProperties(eavropProperties);
			//State transition ON_HOLD -> ACCEPTED
			//TODO:H00RHG
			//eavrop.setEavropState(new AcceptedEavropState());
			handleEavropRestarted(eavrop.getEavropId());
		
		}else if(BookingDeviationResponseType.STOP.equals(responseType)) {
			//State transition ON_HOLD -> APPROVED
			//TODO:H00RHG
			//eavrop.setEavropState(new ApprovedEavropState());
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
			note = new Note(NoteType.DEVIATION_RESPONSE, comment, person);
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
		Eavrop eavrop = eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new IllegalArgumentException("Eavrop with EavropId:" + eavropId.toString() + " does not exist");
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

		getEventBus().post(event);
	}

	private void handleBookingDeviation(EavropId eavropId, BookingId bookingId){
		BookingDeviationEvent event = new BookingDeviationEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingDeviationEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}

	private void handleInterpreterBookingDeviation(EavropId eavropId, BookingId bookingId){
		InterpreterBookingDeviationEvent event = new InterpreterBookingDeviationEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("InterpreterBookingDeviationEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}

	private void handleEavropRestarted(EavropId eavropId){
		EavropRestartedByBestallareEvent event = new EavropRestartedByBestallareEvent(eavropId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropRestartedByBestallareEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}

	private void handleEavropClosedByBestallare(EavropId eavropId){
		EavropClosedByBestallareEvent event = new EavropClosedByBestallareEvent(eavropId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropClosedByBestallareEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}
}
