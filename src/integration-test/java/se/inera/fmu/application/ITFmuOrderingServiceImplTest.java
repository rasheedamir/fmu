package se.inera.fmu.application;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.inera.fmu.Application;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.TestUtil;
import static org.junit.Assert.*;

/**
 * Created by Rasheed on 8/8/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
public class ITFmuOrderingServiceImplTest {
	
	@Inject
	private CurrentUserService currentUserService;
	
	@Inject
	private FmuOrderingService fmuOrderingService;
	
	
	@Before
	public void setUp(){
		TestUtil.loginWithNoActiveRole();
	}

    @Test
    public void serviceShouldReturnAllEavropsForUserLandstingSamordnare() {
    	this.currentUserService.getCurrentUser().setActiveRole(Role.LANDSTINGSSAMORDNARE);
		List<Eavrop> eavrops = fmuOrderingService.getOverviewEavrops();
		assertEquals(eavrops.size(), 1);
		for (Eavrop eavrop : eavrops) {
			
		}
    }
}
