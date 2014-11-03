package se.inera.fmu.application.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.stereotype.Service;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.domain.model.authentication.Role;
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

	@Override
	public void changeRole(Role role) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		ExpiringUsernameAuthenticationToken token = (ExpiringUsernameAuthenticationToken) authentication;
		
		FmuUserDetails details = (FmuUserDetails) token.getPrincipal();
		User user = details.getUser();
		user.setActiveRole(role);
		
		ExpiringUsernameAuthenticationToken newToken = new ExpiringUsernameAuthenticationToken(token.getTokenExpiration(), details, token.getCredentials(), details.getAuthorities());
		
		context.setAuthentication(newToken);
	}

}
