package se.inera.fmu.infrastructure.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
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
public class EavropListener implements EventBusListener {

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropAcceptedByVardgivarenhetEvent(EavropAcceptedByVardgivarenhetEvent eavropAcceptedByVardgivarenhetEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropAssignedToVardgivarenhetEvent(EavropAssignedToVardgivarenhetEvent eavropAssignedToVardgivarenhetEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRejectedByVardgivarenhetEvent(EavropRejectedByVardgivarenhetEvent eavropRejectedByVardgivarenhetEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingCreatedEvent(BookingCreatedEvent bookingCreatedEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationEvent(BookingDeviationEvent bookingDeviationEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleBookingDeviationResponseEvent(BookingDeviationResponseEvent bookingDeviationResponseEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentRequestedEvent(DocumentRequestedEvent documentRequestedEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleDocumentsSentFromBestallareEvent(DocumentSentByBestallareEvent documentSentByBestallareEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygApprovedByBestallareEvent(IntygApprovedByBestallareEvent intygApprovedByBestallareEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygComplemetsRequestedFromBestallareEvent(IntygComplemetsRequestedFromBestallareEvent intygComplemetsRequestedFromBestallareEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleIntygSentEvent(IntygSentEvent intygSentEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropCreatedEvent(EavropCreatedEvent eavropCreatedEvent) {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEavropRestartedByBestallareEvent(EavropRestartedByBestallareEvent eavropRestartedByBestallareEvent) {

    }
}
