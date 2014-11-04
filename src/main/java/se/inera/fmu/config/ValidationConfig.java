package se.inera.fmu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ValidationConfig extends WebMvcConfigurerAdapter{
 
    /**
     * Method validation post processor. This bean is created to apply the JSR validation in method
     * parameters. Any class which want to perform method param validation must use @Validated
     * annotation at class level.
     * 
     * @return the method validation post processor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}