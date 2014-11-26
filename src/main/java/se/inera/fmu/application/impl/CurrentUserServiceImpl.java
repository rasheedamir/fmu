package se.inera.fmu.application.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.stereotype.Service;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.infrastructure.security.FakeCredentials;
import se.inera.fmu.infrastructure.security.FmuUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	@Override
	public User getCurrentUser() {
        User user = new User();
        user.setFirstName("Åsa");
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.ROLE_SAMORDNARE);
        roles.add(Role.ROLE_UTREDARE);
        user.setRoles(roles);
        user.setMiddleAndLastName("Andersson");
        user.setVardenhetHsaId("IFV1239877878-1045");
        user.setHsaId("IFV1239877878-1042");
        user.setActiveRole(Role.ROLE_SAMORDNARE);
        user.setLandstingCode(1);
		return user;
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
