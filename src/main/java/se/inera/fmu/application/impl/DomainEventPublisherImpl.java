package se.inera.fmu.application.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import se.inera.fmu.application.DomainEventPublisher;

import com.google.common.eventbus.AsyncEventBus;

/**
 * Created by Rasheed on 11/5/14.
 */
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    @Inject
    private AsyncEventBus asyncEventBus;

    @Override
    public void post(Object event) {
        asyncEventBus.post(event);
    }
}
