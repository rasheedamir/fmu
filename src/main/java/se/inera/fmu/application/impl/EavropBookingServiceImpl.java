package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;

/**
 * 
 * @see EavropBookingService
 *
 */
@Service
@Validated
@Slf4j
public class EavropBookingServiceImpl implements EavropBookingService {

    private final EavropRepository eavropRepository;
 
    /**
     * Constructor
     * @param eavropRepository
     */
	@Inject
	public EavropBookingServiceImpl(EavropRepository eavropRepository) {
		this.eavropRepository = eavropRepository;
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public BookingId createBooking(CreateBookingCommand aCommand){
		//Find Eavrop
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		//Create booking
		Booking booking = new Booking(aCommand.getBookingType(), aCommand.getBookingStartDateTime(), aCommand.getBookingEndDateTime(), aCommand.getAdditionalService(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getUseInterpreter());
		//Add booking to eavrop
		eavrop.addBooking(booking);

        if(log.isDebugEnabled()){
        	log.debug(String.format("booking created :: %s", booking.toString()));
        }
        return booking.getBookingId();
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void changeBookingStatus(ChangeBookingStatusCommand aCommand){
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());

		Note deviationNote = createDeviationNote(aCommand.getComment(), aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		eavrop.setBookingStatus(aCommand.getBookingId(), aCommand.getBookingStatus(),deviationNote);
		
		if(log.isDebugEnabled()){
        	log.debug(String.format("Status changed for booking:: %s", aCommand.getBookingId().toString()));
        }
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand aCommand){
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		InterpreterBookingStatusType interpreterBookingStatus = aCommand.getInterpreterbookingStatus();
		
		Note deviationNote = createDeviationNote(aCommand.getComment(), aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		
		eavrop.setInterpreterBookingStatus(aCommand.getBookingId(), interpreterBookingStatus, deviationNote);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Status changed for interpreter booking:: %s", aCommand.getBookingId().toString()));
        }
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId addBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand ){
		//Look up eavrop 
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		BookingDeviationResponse bookingDeviationResponse = createBookingDeviationResponse(aCommand);
		
		eavrop.addBookingDeviationResponse(aCommand.getBookingId(), bookingDeviationResponse);

		if(log.isDebugEnabled()){
        	log.debug(String.format("Booking deviation added for booking:: %s", aCommand.getBookingId().toString()));
        }
		return eavrop.getEavropId();
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

	private BookingDeviationResponse  createBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand ) {
		return new BookingDeviationResponse(aCommand.getResponseType(), aCommand.getResponseTimestamp(), aCommand.getBestallaradministrator(), createResponseNote(aCommand.getResponseComment(), aCommand.getBestallaradministrator()));
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
}
