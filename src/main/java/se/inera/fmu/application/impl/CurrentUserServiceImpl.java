package se.inera.fmu.application.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.infrastructure.security.FmuUserDetails;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	@Override
	public User getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		FmuUserDetails details = (FmuUserDetails) authentication.getPrincipal();
		
		return details.getUser();
	}

}
