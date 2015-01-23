package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.FmuEventService;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropClosedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropStartEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropRejectedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentRequestedEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentSentByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplemetsRequestedFromBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentEvent;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Slf4j

//TODO: refactor to remove need for transactions  
public class FmuEventServiceImpl implements FmuEventService {

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher; 
    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public FmuEventServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	@Transactional(readOnly=true)
	public void publishEavropCreatedEvent(ArendeId arendeId) {
		Eavrop eavrop = getEavropByArendeId(arendeId);
		
		EavropCreatedEvent event = new EavropCreatedEvent(eavrop.getEavropId(), arendeId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropCreatedEvent created :: %s", event.toString()));
        }
		domainEventPublisher.post(event);
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public void publishDocumentsSentByBestallareEvent(ArendeId arendeId, DateTime dateTimeDocumentsSent) {
		//TODO: don't know if this event should be created... 
		Eavrop eavrop = getEavropByArendeId(arendeId);
		DocumentSentByBestallareEvent event = new DocumentSentByBestallareEvent(eavrop.getEavropId(), arendeId, dateTimeDocumentsSent);
        if(log.isDebugEnabled()){
        	log.debug(String.format("DocumentSentByBestallareEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void publishEavropStartEvent(ArendeId arendeId){
		//TODO: dont know if this event should be created... 
		Eavrop eavrop = getEavropByArendeId(arendeId);
		EavropStartEvent event = new EavropStartEvent(eavrop.getEavropId(), arendeId, eavrop.getStartDate().toDateTimeAtStartOfDay());
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropStartEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	@Transactional(readOnly=true)
	public void publishDocumentRequestedEvent(EavropId eavropId, RequestedDocument requestedDocument) {
		//TODO: only eavropId and DocumentId
		Eavrop eavrop = getEavropByEavropId(eavropId);
		DocumentRequestedEvent event = new DocumentRequestedEvent(eavropId, eavrop.getArendeId(), requestedDocument.getId(), requestedDocument.getDocumentName(), requestedDocument.getDocumentDateTime(), requestedDocument.getPerson(), requestedDocument.getRequestNote());
		if(log.isDebugEnabled()){
			log.debug(String.format("DocumentRequestedEvent created :: %s", event.toString()));
		}
	    domainEventPublisher.post(event);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void publishBookingCreatedEvent(EavropId eavropId, BookingId bookingId) {
		//TODO: change to only publish eavropId and bookingId, then remove transactional annotation
		Eavrop eavrop = getEavropByEavropId(eavropId);
		Booking booking = eavrop.getBooking(bookingId);
		BookingType bookingType = booking.getBookingType();
		DateTime bookingStart = booking.getStartDateTime();
		DateTime bookingEnd = booking.getEndDateTime();
		String resourceName = (booking.getBookingResource()!=null)?booking.getBookingResource().getName():null;
		String resourceRole = (booking.getBookingResource()!=null)?booking.getBookingResource().getRole():null;
		boolean isInterpreter = (booking.getInterpreterBooking()!=null)?Boolean.TRUE:Boolean.FALSE;
		
		BookingCreatedEvent event = new BookingCreatedEvent(eavropId, eavrop.getArendeId(), bookingId, bookingType, 
				bookingStart, bookingEnd, resourceName, resourceRole,  isInterpreter);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingCreatedEvent created :: %s", event.toString()));
        }

        domainEventPublisher.post(event);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void publishBookingDeviationEvent(EavropId eavropId,	BookingId bookingId) {
		Eavrop eavrop = getEavropByEavropId(eavropId);
		Booking booking = eavrop.getBooking(bookingId);

		if(booking == null){
			return;
		}
		BookingDeviationType bookingDeviationType = BookingDeviationType.convert(booking.getBookingStatus());
		Note deviationNote = booking.getDeviationNote();
		
		boolean isReasonForOnhold = BookingDeviationTypeUtil.isBookingStatusReasonForOnHold(booking.getBookingStatus(), eavrop.getUtredningType());

		BookingDeviationEvent event = new BookingDeviationEvent(eavropId, eavrop.getArendeId(), bookingId, bookingDeviationType, isReasonForOnhold, deviationNote);
        if(log.isDebugEnabled()){
        	log.debug(String.format("BookingDeviationEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	public void publishInterpreterBookingDeviationEvent(EavropId eavropId, BookingId bookingId) {
		InterpreterBookingDeviationEvent event = new InterpreterBookingDeviationEvent(eavropId, bookingId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("InterpreterBookingDeviationEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	public void publishEavropRestartedByBestallareEvent(EavropId eavropId){
		EavropRestartedByBestallareEvent event = new EavropRestartedByBestallareEvent(eavropId);
	    if(log.isDebugEnabled()){
	    	log.debug(String.format("EavropRestartedByBestallareEvent created :: %s", event.toString()));
	    }
	    domainEventPublisher.post(event);
	}

	@Override
	public void publishEavropClosedByBestallareEvent(EavropId eavropId){
		EavropClosedByBestallareEvent event = new EavropClosedByBestallareEvent(eavropId);
	    if(log.isDebugEnabled()){
	    	log.debug(String.format("EavropClosedByBestallareEvent created :: %s", event.toString()));
	    }
	    domainEventPublisher.post(event);
	}

	private Eavrop getEavropByArendeId(ArendeId arendeId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop with arendeId %s not found", arendeId.toString()));
		}
		return eavrop;
	}
	
	private Eavrop getEavropByEavropId(EavropId eavropId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}

	@Override
	public void publishIntygSentEvent(EavropId eavropId, ArendeId arendeId, DateTime intygSentDateTime) {
		IntygSentEvent event = new IntygSentEvent(eavropId, arendeId, intygSentDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSentEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	public void publishIntygComplemetsRequestedFromBestallareEvent(EavropId eavropId, DateTime complementRequestDateTime) {
		IntygComplemetsRequestedFromBestallareEvent event = new IntygComplemetsRequestedFromBestallareEvent(eavropId, complementRequestDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygComplemetsRequestedFromBestallareEvent created :: %s", event.toString()));
        }
	    domainEventPublisher.post(event);
	}

	@Override
	public void publishIntygApprovedByBestallareEvent(EavropId eavropId, DateTime intygApprovedDateTime) {
		IntygApprovedByBestallareEvent event = new IntygApprovedByBestallareEvent(eavropId, intygApprovedDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygApprovedByBestallareEvent created :: %s", event.toString()));
        }
	    domainEventPublisher.post(event);
	}

	@Override
	public void publishEavropApprovedByBestallareEvent(EavropId eavropId, DateTime approvedDateTime) {
		//TODO: Should we have events when eavrop is approved?	
		EavropApprovedByBestallareEvent event = new EavropApprovedByBestallareEvent(eavropId, approvedDateTime);
		if(log.isDebugEnabled()){
			log.debug(String.format("EavropApprovedByBestallareEvent created :: %s", event.toString()));
		}
		domainEventPublisher.post(event);
	}

	@Override
	public void publishEavropCompensationApprovedByBestallareEvent(	EavropId eavropId, DateTime compensationApprovalDateTime) {
		//TODO: Should we have events when eavrop compensation is approved?	
		EavropCompensationApprovedByBestallareEvent event = new EavropCompensationApprovedByBestallareEvent(eavropId, compensationApprovalDateTime);
		if(log.isDebugEnabled()){
			log.debug(String.format("EavropCompensationApprovedByBestallareEvent created :: %s", event.toString()));
		}
		domainEventPublisher.post(event);
	}

	@Override
	public void publishEavropAssignedToVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId) {
		EavropAssignedToVardgivarenhetEvent event = new EavropAssignedToVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAssignedToVardgivarenhetEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	@Transactional(readOnly=true)
	public void publishEavropAcceptedByVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId) {
		Eavrop eavrop = getEavropByEavropId(eavropId);
		EavropAcceptedByVardgivarenhetEvent event = new EavropAcceptedByVardgivarenhetEvent(eavropId, eavrop.getArendeId(), vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAcceptedByVardgivarenhetEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}

	@Override
	@Transactional(readOnly=true)
	public void publishEavropRejectedByVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId) {
		Eavrop eavrop = getEavropByEavropId(eavropId);
		EavropRejectedByVardgivarenhetEvent event = new EavropRejectedByVardgivarenhetEvent(eavropId, eavrop.getArendeId(), vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropRejectedByVardgivarenhetEvent created :: %s", event.toString()));
        }
        domainEventPublisher.post(event);
	}
}
