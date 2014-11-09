package se.inera.fmu.application;

/**
 * Created by Rasheed on 11/5/14.
 */
public interface DomainEventPublisher {

    /**
     * Posts an event to all registered subscribers.  This method will return
     * successfully after the event has been posted to all subscribers, and
     * regardless of any exceptions thrown by subscribers.
     *
     * <p>If no subscribers have been subscribed for {@code event}'s class, and
     * {@code event} is not already a {@link com.google.common.eventbus.DeadEvent}, it will be wrapped in a
     * DeadEvent and reposted.
     *
     * @param event  event to post.
     */
    public void post(Object event);

}
