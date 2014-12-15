package se.inera.fmu.infrastructure.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    public String getCurrentAuditor() {
    	return "Change_me";
    }
}
