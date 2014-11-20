package se.inera.fmu.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.SakerhetstjanstAssertion;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.landsting.Landstingssamordnare;
import se.inera.fmu.domain.model.landsting.LandstingssamordnareRepository;

/**
 * @author andreaskaltenbach
 */
public class FmuUserDetailsService implements SAMLUserDetailsService {

	private static final Logger LOG = LoggerFactory
			.getLogger(FmuUserDetailsService.class);
	
	@Autowired
	private LandstingssamordnareRepository ltSamordnareRepo;


	@Override
	public Object loadUserBySAML(SAMLCredential credential) {

		LOG.info("User authentication was successful. SAML credential is: {}",
				credential);

		SakerhetstjanstAssertion assertion = new SakerhetstjanstAssertion(
				credential.getAuthenticationAssertion());

		User user = createFmuUser(assertion);
		
		if(user.getVardenhetHsaId() != null && user.getVardenhetHsaId().isEmpty() == false){
			user.getRoles().add(Role.ROLE_UTREDARE);	
		}
		
		Landstingssamordnare landstingssamordnare = ltSamordnareRepo.findByHsaId(new HsaId(user.getHsaId()));
		if(landstingssamordnare != null){
			user.getRoles().add(Role.ROLE_SAMORDNARE);
			user.setLandstingCode(landstingssamordnare.getLandsting().getLandstingCode().getCode());
		}
		
		user.setActiveRole(user.getRoles().get(0));
		
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
