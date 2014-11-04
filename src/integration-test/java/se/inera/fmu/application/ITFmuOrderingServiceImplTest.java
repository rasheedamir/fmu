package se.inera.fmu.application;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import se.inera.fmu.Application;
import se.inera.fmu.application.impl.FmuOrderingServiceImpl;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.landsting.LandstingRepository;

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
	@Mock
	private CurrentUserService currentUserService;
	
	@Inject
	private LandstingRepository landstingRepository;
	
	@Inject 
	private EavropRepository eavropRepository;
	
	@InjectMocks
	private FmuOrderingServiceImpl fmuOrderingService;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(fmuOrderingService, "eavropRepository", eavropRepository);
		ReflectionTestUtils.setField(fmuOrderingService, "landstingRepository", landstingRepository);
	}

    @Test
    public void serviceShouldReturnAllEavropsForUserLandstingSamordnare() {
    	User landstingSamordnare = new User();
    	ArrayList<Role> roles = new ArrayList<Role>();
    	roles.add(Role.LANDSTINGSSAMORDNARE);
    	landstingSamordnare.setRoles(roles);
    	landstingSamordnare.setActiveRole(Role.LANDSTINGSSAMORDNARE);
		when(currentUserService.getCurrentUser()).thenReturn(landstingSamordnare);
        
		List<Eavrop> eavrops = fmuOrderingService.getOverviewEavrops();
		assertEquals(eavrops.size(), 1);
    }
}
