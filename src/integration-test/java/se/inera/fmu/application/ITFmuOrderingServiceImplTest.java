package se.inera.fmu.application;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.inera.fmu.Application;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
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
    	User currentUser = this.currentUserService.getCurrentUser();
    	currentUser.setActiveRole(Role.LANDSTINGSSAMORDNARE);
    	EavropStateType state = EavropStateType.UNASSIGNED;
		DateTime fromDate = new DateTime(1990, 1, 1, 0, 0);
		DateTime toDate = new DateTime(2012,12,1, 0, 0);
		currentUser.setLandstingCode(1);
		
		Page<Eavrop> eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(), state, null);
		assertNotEquals(eavrops, null);
		for (Eavrop eavrop : eavrops) {
			assertEquals(eavrop.getLandsting().getLandstingCode(), currentUser.getLandstingCode());
		}
    }
}
