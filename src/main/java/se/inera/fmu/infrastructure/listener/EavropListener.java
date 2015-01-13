package se.inera.fmu.infrastructure.listener;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropStartEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropRejectedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentRequestedEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentSentByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplemetsRequestedFromBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentEvent;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.infrastructure.mail.MailService;
import se.inera.fmu.interfaces.managing.command.PublishFmuAssignmentResponseCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuBookingCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuBookingDeviationCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuDocumentRequestedCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuIntygSentCommand;
import se.inera.fmu.interfaces.managing.command.PublishFmuStartDateCommand;
import se.inera.fmu.interfaces.managing.ws.BestallareClient;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Created by Rasheed on 9/30/14.
 */
@SuppressWarnings("ALL")
@Component
@Slf4j
public class EavropListener implements EventBusListener {

    @Inject
    private MailService mailService;

    @Inject
    private BestallareClient bestallareWebserviceClient;

    @Inject
    private EavropRepository eavropRepository;

    @Inject
    private VardgivarenhetRepository vardgivarenhetRepository;

    /**
     * Handles EavropAcceptedByVardgivarenhetEvent
     * A web service call is made to the bestallare to communicate that the Eavrop FMU assignment have been accepted and by 
     * which vardgivarenhet  
     * @param event: EavropAcceptedByVardgivarenhetEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    @Transactional
    public void handleEavropAcceptedByVardgivarenhetEvent(final EavropAcceptedByVardgivarenhetEvent event) {
        log.debug("EavropAcceptedByVardgivarenhetEvent received for ARENDE ID {} EAVROP ID {} HSA ID {} ", event.getArendeId().toString(), event.getEavropId().toString(), event.getHsaId().toString());
        
        Vardgivarenhet vardgivarenhet = getVardgivarenhet(event.getHsaId());

        //TODO: Add phone and email to vardgivarenhet entity?
        PublishFmuAssignmentResponseCommand assignmentResponseCommand = 
        		new PublishFmuAssignmentResponseCommand(event.getArendeId(), Boolean.TRUE, vardgivarenhet.getUnitName(), vardgivarenhet.getVardgivare().getName() , vardgivarenhet.getAddress().getAddress1(), vardgivarenhet.getAddress().getPostalCode(), vardgivarenhet.getAddress().getCity(), vardgivarenhet.getAddress().getCountry(), null, null);
        
        bestallareWebserviceClient.publishFmuAssignmentResponse(assignmentResponseCommand);
        log.debug("FmuAssignmentResponse  published for ARENDE ID {} EAVROP ID {} ",  event.getArendeId().toString(), event.getEavropId());
    }

    /**
     *
     * @param event: EavropAssignedToVardgivarenhetEvent
     */

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropAssignedToVardgivarenhetEvent(final EavropAssignedToVardgivarenhetEvent event) {
        log.debug("EavropAssignedToVardgivarenhetEvent received for EAVROP ID {} HSA ID {} ", event.getEavropId().toString(), event.getHsaId().toString());
        
    }


    /**
     * Handles EavropRejectedByVardgivarenhetEvent
     * A web service call is made to the bestallare to communicate that the Eavrop FMU assignment rejected and by 
     * which vardgivarenhet  
     * @param event: EavropRejectedByVardgivarenhetEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    @Transactional
    public void handleEavropRejectedByVardgivarenhetEvent(final EavropRejectedByVardgivarenhetEvent event) {
        log.debug("EavropRejectedByVardgivarenhetEvent received for ARENDE ID {} EAVROP ID {} HSA ID {} ", event.getArendeId().toString(), event.getEavropId().toString(), event.getHsaId().toString());
        
        Vardgivarenhet vardgivarenhet = getVardgivarenhet(event.getHsaId());
        PublishFmuAssignmentResponseCommand assignmentResponseCommand = 
        		new PublishFmuAssignmentResponseCommand(event.getArendeId(), Boolean.FALSE, vardgivarenhet.getUnitName(), vardgivarenhet.getVardgivare().getName() , vardgivarenhet.getAddress().getAddress1(), vardgivarenhet.getAddress().getPostalCode(), vardgivarenhet.getAddress().getCity(), vardgivarenhet.getAddress().getCountry(), null, null);
        
        bestallareWebserviceClient.publishFmuAssignmentResponse(assignmentResponseCommand);
        log.debug("FmuAssignmentResponse  published for ARENDE ID {} EAVROP ID {} ",  event.getArendeId().toString(), event.getEavropId().toString());

    }

    /**
     * Handles EavropBookingCreatedEvent
     * A web service call is made to the bestallare to communicate that a booking have been added to the Eavrop FMU 
     * @param event: BookingCreatedEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingCreatedEvent(final BookingCreatedEvent event) {
        log.debug("BookingCreatedEvent received for ARENDE ID {} EAVROP ID {} BOOKING ID {} ", event.getArendeId().toString(), event.getEavropId().toString(), event.getBookingId().toString());
        
        PublishFmuBookingCommand publishFmuBookingCommand = new PublishFmuBookingCommand(event.getArendeId(), event.getBookingId(), event.getBookingType(), event.getStartDateTime(), event.getEndDateTime(), event.getResourceName(), event.getResourceRole(), event.isInterpreter());
        bestallareWebserviceClient.publishFmuBooking(publishFmuBookingCommand);
        log.debug("FmuBooking  published for ARENDE ID {} EAVROP ID {} BOOKING ID {} ",  event.getArendeId().toString(), event.getEavropId().toString(), event.getBookingId().toString());
    }

    /**
     * Handles EavropBookingDeviationEvent
     * A web service call is made to the bestallare to communicate that a booking deviation have occured on the Eavrop FMU 
     * @param event: BookingDeviationEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationEvent(final BookingDeviationEvent event) {
        log.debug("BookingDeviationEvent received for ARENDE ID {} EAVROP ID {} BOOKING ID {} ", event.getArendeId().toString(), event.getEavropId().toString(), event.getBookingId().toString());
       
        PublishFmuBookingDeviationCommand publishFmuBookingDeviationCommand = new PublishFmuBookingDeviationCommand(event.getArendeId(), event.getBookingId(), event.getBookingDeviationType(), event.isBookingDeviationResponseRequired(), event.getBookingDeviationNote());
        bestallareWebserviceClient.publishFmuBookingDeviation(publishFmuBookingDeviationCommand);
        log.debug("FmuBookingDeviation  published for ARENDE ID {} EAVROP ID {} BOOKING ID {} ",  event.getArendeId().toString(), event.getEavropId().toString(), event.getBookingId().toString());
    }

    /**
     * Handles BookingDeviationResponseEvent
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationResponseEvent(final BookingDeviationResponseEvent event) {
    	log.debug("BookingDeviationEvent received for EAVROP ID {} ", event.getEavropId().toString());
        //Mail someone?
    }

    /**
     * Handles DocumentRequestedEvent
     * A web service call is made to the bestallare to communicate that a document have been requested on a Eavrop FMU 
	 *
     * @param event DocumentRequestedEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentRequestedEvent(final DocumentRequestedEvent event) {
        log.debug("DocumentRequestedEvent received for ARENDE ID {} EAVROP ID {} DOCUMENT ID {} ", event.getArendeId().toString(), event.getEavropId().toString(), event.getDocumentId().toString());
        PublishFmuDocumentRequestedCommand publishFmuDocumentRequestedCommand = new PublishFmuDocumentRequestedCommand(event.getArendeId(), event.getDocumentName(), event.getDocumentDateTime(), event.getRequestPerson(), event.getRequestNote());
        bestallareWebserviceClient.publishFmuDocumentRequested(publishFmuDocumentRequestedCommand);
        log.debug("FmuDocumentRequest published for ARENDE ID {} EAVROP ID {} DOCUMENT ID {} ",  event.getArendeId().toString(), event.getEavropId().toString(), event.getDocumentId().toString());
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentsSentFromBestallareEvent(final DocumentSentByBestallareEvent event) {
    	log.debug("DocumentsSentFromBestallareEvent received for ARENDE ID {} EAVROP ID {} ", event.getArendeId().toString(), event.getEavropId().toString() );
        //Mail someone?
    }
    
    /**
     * Handles EavropStartEvent
     * A web service call is made to the bestallare to communicate the calculated start date of the Eavrop FMU 
	 *
     * @param event EavropStartEvent
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropStartEvent(final EavropStartEvent event) {
        log.debug("EavropStartEvent received for ARENDE ID {} EAVROP ID {} ", event.getArendeId().toString(), event.getEavropId().toString());
        //Mail someone?
        
        //web service  call to customer with start date
        PublishFmuStartDateCommand publishFmuStartDate = new PublishFmuStartDateCommand(event.getArendeId(), event.getEavropStartDate());
        bestallareWebserviceClient.publishFmuStartDate(publishFmuStartDate);
        log.debug("FmuStartDate published for ARENDE ID {} EAVROP ID {} ",  event.getArendeId().toString(), event.getEavropId().toString());
        
    }


    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygApprovedByBestallareEvent(final IntygApprovedByBestallareEvent event) {
        log.debug("IntygApprovedByBestallareEvent received for EAVROP ID {} ", event.getEavropId().toString());

        //mail someone?
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygComplemetsRequestedFromBestallareEvent(final IntygComplemetsRequestedFromBestallareEvent event) {
        log.debug("IntygComplemetsRequestedFromBestallareEvent received for EAVROP ID {} ", event.getEavropId().toString());
        //Mail someone?
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygSentEvent(final IntygSentEvent event) {
    	log.debug("IntygSentEvent received for EAVROP ID {} ", event.getEavropId().toString());
    	//web service  call to customer with intyg sent information
        PublishFmuIntygSentCommand publishFmuIntygSentCommand = new PublishFmuIntygSentCommand(event.getArendeId(), event.getSentDateTime());
        bestallareWebserviceClient.publishFmuIntygSent(publishFmuIntygSentCommand);
        log.debug("FmuIntygSent information published for ARENDE ID {} EAVROP ID {} ", event.getArendeId().toString(), event.getEavropId().toString());
    }

    /**
     * Method handles the eavrop created event which is to send mail to stakeholders
     * @param event EavropCreatedEvent that carries event information about the created eavrop
     */
    @Subscribe
    @AllowConcurrentEvents
    @Transactional(readOnly=true)
    public void handleEavropCreatedEvent(EavropCreatedEvent event) {
    	log.debug("EavropCreatedEvent received for ARENDE ID {} EAVROP ID {} ", event.getArendeId().toString(), event.getEavropId().toString());
    	Eavrop eavrop = getEavrop(event.getEavropId());
        this.mailService.sendEavropCreatedEmail(eavrop.getEavropId(), eavrop.getArendeId(), eavrop.getUtredningType(), eavrop.getLastValidEavropAssignmentAcceptDay(), eavrop.getLandsting().getLandstingCode());
        log.debug("Eavrop created email notification published for ARENDE ID {} EAVROP ID {} ",  event.getArendeId().toString(), event.getEavropId().toString());
    }
    
    
    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRestartedByBestallareEvent(final EavropRestartedByBestallareEvent event) {
    	log.debug("EavropRestartedByBestallareEvent received for EAVROP ID {} ", event.getEavropId().toString());
        //Mail someone?
    }

    private Vardgivarenhet getVardgivarenhet(HsaId hsaId){
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(hsaId);
    	if(vardgivarenhet == null){
    		throw new EntityNotFoundException(String.format("Vargivarenhet with hsaId: %s could not be found", hsaId.toString()));
    	}
    	return vardgivarenhet;
    }
    
    private Eavrop getEavrop(EavropId eavropId){
    	Eavrop eavrop = eavropRepository.findByEavropId(eavropId);
    	if(eavrop == null){
    		throw new EntityNotFoundException(String.format("Eavrop with eavropId: %s could not be found", eavropId.toString()));
    	}
    	return eavrop;
    }

}
