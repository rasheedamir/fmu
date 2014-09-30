package se.inera.fmu.infrastructure.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Created by Rasheed on 9/30/14.
 */
public class NewEavropCreatedListener implements EventBusListener {

    @Subscribe
    @AllowConcurrentEvents
    public void handleNewEavropCreatedEvent() {

    }
}
