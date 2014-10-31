package se.inera.fmu.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion;
import se.inera.fmu.domain.model.authentication.User;

/**
 * @author andreaskaltenbach
 */
public class FmuUserDetailsService implements SAMLUserDetailsService {

	private static final Logger LOG = LoggerFactory
			.getLogger(FmuUserDetailsService.class);


	@Override
	public Object loadUserBySAML(SAMLCredential credential) {

		LOG.info("User authentication was successful. SAML credential is: {}",
				credential);

		SakerhetstjanstAssertion assertion = new SakerhetstjanstAssertion(
				credential.getAuthenticationAssertion());

		//TODO: Assign available roles correctly
		User user = createFmuUser(assertion);
		user.getRoles().add(Role.UTREDARE);
		user.getRoles().add(Role.LANDSTINGSSAMORDNARE);
		user.setActiveRole(Role.UTREDARE);
		
		return new FmuUserDetails(user); 

	}

	private User createFmuUser(SakerhetstjanstAssertion assertion) {
		User u = new User();
		u.setFirstName(assertion.getFornamn());
		u.setMiddleAndLastName(assertion.getMellanOchEfternamn());
		u.setHsaId(assertion.getHsaId());
		u.setVardenhetHsaId(assertion.getEnhetHsaId());
		
		return u;
	}

}
