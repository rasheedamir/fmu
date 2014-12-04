package se.inera.fmu.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Rasheed on 9/29/14.
 *
 */
@Component
@Slf4j
public class EventBusRegisterBeanPostProcessor implements BeanPostProcessor {

    @Inject
    private AsyncEventBus asyncEventBus; // The only event bus i assume...

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {

        final Class<? extends Object> clazz = bean.getClass();

        final Method[] methods = clazz.getMethods();
        for (final Method method : methods) {

            final Annotation[] annotations = method.getAnnotations();
            for (final Annotation annotation : annotations) {

                final Class<? extends Annotation> annotationType = annotation.annotationType();
                final boolean subscriber = annotationType.equals(Subscribe.class);
                if (subscriber) {
                    asyncEventBus.register(bean);
                    log.info(beanName + " - Registered with AsyncEventBus!");
                    return bean;
                }
            }
        }

        return bean;
    }

}
