package se.inera.fmu.application;

import static org.junit.Assert.*;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.inera.fmu.Application;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.interfaces.managing.rest.TestUtil;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OVERVIEW_EAVROPS_STATES;

/**
 * Created by Rasheed on 8/8/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
@Slf4j
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
		DateTime fromDate = new DateTime(1990, 1, 1, 0, 0);
		DateTime toDate = new DateTime(2052,12,1, 0, 0);
		currentUser.setLandstingCode(1);
		Pageable pageSpecs = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "eavropId"));
		
		// NOT ACCEPTED
		Page<Eavrop> eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.NOT_ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("NOT ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		DateTime previous = null;
		for (Eavrop eavrop : eavrops) {
			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.UNASSIGNED
					|| eavrop.getStatus() == EavropStateType.ASSIGNED);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
		
		// ACCEPTED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		previous = null;
		for (Eavrop eavrop : eavrops) {
			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.ACCEPTED
					|| eavrop.getStatus() == EavropStateType.ON_HOLD);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
		
		// COMPLETED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.COMPLETED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("COMPLETED: "+ Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		previous = null;
		for (Eavrop eavrop : eavrops) {
			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.CLOSED
					|| eavrop.getStatus() == EavropStateType.APPROVED);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
    }

    @Test
    public void serviceShouldReturnAllEavropsForUserUtredare() {
    	User currentUser = this.currentUserService.getCurrentUser();
    	currentUser.setActiveRole(Role.UTREDARE);
		DateTime fromDate = new DateTime(1990, 1, 1, 0, 0);
		DateTime toDate = new DateTime(2052,12,1, 0, 0);
		Pageable pageSpecs = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "eavropId"));
		
		// NOT ACCEPTED
		Page<Eavrop> eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.NOT_ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("NOT ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		DateTime previous = null;
		for (Eavrop eavrop : eavrops) {
//			assertTrue(eavrop.get);
			assertTrue(eavrop.getStatus() == EavropStateType.UNASSIGNED
					|| eavrop.getStatus() == EavropStateType.ASSIGNED);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
		
		// ACCEPTED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		previous = null;
		for (Eavrop eavrop : eavrops) {
//			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.ACCEPTED
					|| eavrop.getStatus() == EavropStateType.ON_HOLD);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
		
		// COMPLETED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OVERVIEW_EAVROPS_STATES.COMPLETED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("COMPLETED: "+ Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getTotalElements() > 0);
		
		previous = null;
		for (Eavrop eavrop : eavrops) {
//			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.CLOSED
					|| eavrop.getStatus() == EavropStateType.APPROVED);
			if(previous != null){
				assertTrue(eavrop.getCreatedDate().getMillis() > previous.getMillis());
			}
		}
    }
}
