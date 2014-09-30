package se.inera.fmu.config;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import se.inera.fmu.infrastructure.listener.EventBusListener;

import javax.inject.Inject;

/**
 * Created by Rasheed on 9/29/14.
 *
 */
public class EventBusRegisterBeanPostProcessor implements BeanPostProcessor,
        ApplicationContextAware {

    private ApplicationContext context;

    @Inject
    private EventBus eventBus; // The only event bus i assume...

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof EventBusListener) {
            registerToEventBus(bean);
        }

        return bean;
    }

    private void registerToEventBus(Object bean) {
        this.eventBus.register(bean);
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }
}
