package se.inera.fmu.config;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executors;

/**
 * Created by Rasheed on 9/26/14.
 */
@Configuration
public class EventBusConfiguration {

    @Bean
    public AsyncEventBus asyncEventBus() {
        AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        return asyncEventBus;
    }
}
