package se.inera.fmu.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import se.inera.fmu.infrastructure.listener.EavropListener;
import se.inera.fmu.infrastructure.listener.EventBusListener;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Rasheed on 9/29/14.
 *
 * EventBusPostProcessor registers Spring beans with EventBus. All beans
 * containing Guava's @Subscribe annotation are registered.
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

        // ToDo: This is a hack and should be removed when the lower comment is fixed!
        if(beanName.equals("eavropListener")) {
            asyncEventBus.register(bean);
            log.info(beanName + " - Registered with AsyncEventBus!");
            return bean;
        }

        final Method[] methods = clazz.getMethods();

        for (final Method method : methods) {

            final Annotation[] annotations = method.getAnnotations();

            // ToDo: Somehow list of annotations is empty and therefore bean is not registered!
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
