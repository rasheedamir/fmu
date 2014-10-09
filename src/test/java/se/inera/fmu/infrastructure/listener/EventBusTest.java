package se.inera.fmu.infrastructure.listener;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by Rasheed on 10/1/14.
 *
 * All unit tests regarding EventBus.
 * https://github.com/bbejeck/guava-blog/blob/master/src/test/java/bbejeck/guava/eventbus/EventBusTest.java
 */
public class EventBusTest {

    private AsyncEventBus asyncEventBus;
    private DeadEventListener deadEventListener;
    private CountDownLatch doneSignal;
    private int numberLongEvents = 10;

    @Before
    public void setUp() {
        asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        deadEventListener = new DeadEventListener();
        asyncEventBus.register(deadEventListener);
    }

    private class DeadEventListener {
        List<DeadEvent> deadEvents = new ArrayList<DeadEvent>();

        @Subscribe
        public void handleDeadEvent(DeadEvent deadEvent) {
            deadEvents.add(deadEvent);
        }
    }

    @Test
    public void testUnregisterNewEavropCreatorListener() {

    }

}
