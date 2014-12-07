package se.inera.fmu.infrastructure.listener;

import javax.inject.Inject;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
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
import se.inera.fmu.interfaces.managing.ws.BestallareClient;

/**
 * Created by Rasheed on 9/30/14.
 */
@Component
@Slf4j
public class EavropListener implements EventBusListener {

    @Inject
    MailService mailService;

    @Inject
    BestallareClient bestallareWebserviceClient;

    @Inject
    VardgivarenhetRepository vardgivarenhetRepository;

    
    @Subscribe
    @AllowConcurrentEvents
    //@Transactional
    public void handleEavropAcceptedByVardgivarenhetEvent(final EavropAcceptedByVardgivarenhetEvent event) {
        log.debug("Event received : " + event);
        //Web service call to customer
        
        Vardgivarenhet vardgivarenhet = getVardgivarenhet(event.getHsaId());

        //TODO: Add phone and email to vardgivarenhet entity?

//WORK IN PROGRESS         
//        PublishFmuAssignmentResponseCommand assignmentResponseCommand = 
//        		new PublishFmuAssignmentResponseCommand(event.getArendeId(), Boolean.TRUE, vardgivarenhet.getUnitName(), vardgivarenhet.getVardgivare().getName() , vardgivarenhet.getAddress().getAddress1(), vardgivarenhet.getAddress().getPostalCode(), vardgivarenhet.getAddress().getCity(), vardgivarenhet.getAddress().getCountry(), null, null);
//        
//        bestallareWebserviceClient.publishFmuAssignmentResponse(assignmentResponseCommand);
    }
    
    private Vardgivarenhet getVardgivarenhet(HsaId hsaId){
    	return vardgivarenhetRepository.findByHsaId(hsaId);
    }

    
    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropAssignedToVardgivarenhetEvent(final EavropAssignedToVardgivarenhetEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRejectedByVardgivarenhetEvent(final EavropRejectedByVardgivarenhetEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingCreatedEvent(final BookingCreatedEvent event) {
        log.debug("Event received : " + event);
        //Web service call to customer
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationEvent(final BookingDeviationEvent event) {
        log.debug("Event received : " + event);
        //Web service call to customer
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationResponseEvent(final BookingDeviationResponseEvent event) {
        log.debug("Event received : " + event);
        //Mail someone?
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentRequestedEvent(final DocumentRequestedEvent event) {
        log.debug("Event received : " + event);
      //Web service call to customer
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentsSentFromBestallareEvent(final DocumentSentByBestallareEvent event) {
        log.debug("Event received : " + event);
        //Mail someone?
        //web service  call to customer with start date
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygApprovedByBestallareEvent(final IntygApprovedByBestallareEvent event) {
        log.debug("Event received : " + event);
        //mail someone?
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygComplemetsRequestedFromBestallareEvent(final IntygComplemetsRequestedFromBestallareEvent event) {
        log.debug("Event received : " + event);
        //Mail someone?
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygSentEvent(final IntygSentEvent event) {
        log.debug("Event received : " + event);
        //?
    }

    /**
     * Method handles the eavrop created event which is to send mail to stakeholders
     * @param event EavropCreatedEvent that carries event information about the created eavrop
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropCreatedEvent(EavropCreatedEvent event) {
        log.debug("Event received : " + event);
        this.mailService.sendEavropCreatedEmail(event.getEavropId(), event.getArendeId(), event.getLandstingCode());

    }
    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRestartedByBestallareEvent(final EavropRestartedByBestallareEvent event) {
        log.debug("Event received : " + event);
        //Mail someone?
        
    }
}
