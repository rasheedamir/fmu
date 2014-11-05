package se.inera.fmu.config;


import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.SpelEvaluationException;

import se.inera.fmu.interfaces.managing.rest.DefaultRestErrorResolver;
import se.inera.fmu.interfaces.managing.rest.RestExceptionHandler;

@Configuration
@ComponentScan("se.inera.fmu.interfaces.managing.rest")
@Slf4j
//TODO Make this work and remove the xml configuration in WebConfigurer
public class RestErrorHandlingConfig {

	private static final String DEFAULT_ERROR_URL = "http://www.inera.se/KONTAKT_KUNDSERVICE/Felanmalan-och-support/";

	@Bean
	public RestExceptionHandler restExceptionResolver() {
		log.debug("Registering " + RestExceptionHandler.class + " bean");
		
		RestExceptionHandler handler = new RestExceptionHandler();
		DefaultRestErrorResolver resolver = new DefaultRestErrorResolver();
		
//		Add more error handling definitions here
		Map<String, String> exceptionMappingDefinitions = new HashMap<String, String>();
		exceptionMappingDefinitions.put(SpelEvaluationException.class.toString(), "400");
		exceptionMappingDefinitions.put("Throwable", "500");
		
		resolver.setDefaultMoreInfoUrl(DEFAULT_ERROR_URL);
		resolver.setExceptionMappingDefinitions(exceptionMappingDefinitions);
		handler.setOrder(0);
		handler.setErrorResolver(resolver);
		
		return handler;
	}
}
