package se.inera.fmu.infrastructure.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
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

/**
 * Created by Rasheed on 9/30/14.
 */
@Component
@Slf4j
public class EavropListener implements EventBusListener {

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropAcceptedByVardgivarenhetEvent(final EavropAcceptedByVardgivarenhetEvent event) {
        log.debug("Event received : " + event);
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
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationEvent(final BookingDeviationEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationResponseEvent(final BookingDeviationResponseEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentRequestedEvent(final DocumentRequestedEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentsSentFromBestallareEvent(final DocumentSentByBestallareEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygApprovedByBestallareEvent(final IntygApprovedByBestallareEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygComplemetsRequestedFromBestallareEvent(final IntygComplemetsRequestedFromBestallareEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygSentEvent(final IntygSentEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropCreatedEvent(final EavropCreatedEvent event) {
        log.debug("Event received : " + event);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRestartedByBestallareEvent(final EavropRestartedByBestallareEvent event) {
        log.debug("Event received : " + event);
    }
}
