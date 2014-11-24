package se.inera.fmu.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.inera.fmu.Application;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.TestUtil;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;

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
	
	@Inject
	private FmuListService fmuListService;
	
	@Before
	public void setUp(){
		TestUtil.loginWithNoActiveRole();
	}
	

    @Test
    public void serviceShouldReturnAllEavropsForUserLandstingSamordnare() {
    	User currentUser = this.currentUserService.getCurrentUser();
    	currentUser.setActiveRole(Role.ROLE_SAMORDNARE);
		DateTime fromDate = new DateTime(1990, 1, 1, 0, 0);
		DateTime toDate = new DateTime(2052,12,1, 0, 0);
		currentUser.setLandstingCode(1);
		Pageable pageSpecs = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "eavropId"));
		
		// NOT ACCEPTED
		EavropPageDTO eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.NOT_ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("NOT ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		DateTime previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
			assertEquals(eavrop.getMottagarenOrganisation(),this.fmuListService.findLandstingByLandstingCode(new LandstingCode(currentUser.getLandstingCode())).getName());
			assertTrue(eavrop.getStatus() == EavropStateType.UNASSIGNED
					|| eavrop.getStatus() == EavropStateType.ASSIGNED);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
		
		// ACCEPTED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("ACCEPTED: " + Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
			assertEquals(eavrop.getMottagarenOrganisation(),this.fmuListService.findLandstingByLandstingCode(new LandstingCode(currentUser.getLandstingCode())).getName());
			assertTrue(eavrop.getStatus() == EavropStateType.ACCEPTED
					|| eavrop.getStatus() == EavropStateType.ON_HOLD);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
		
		// COMPLETED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.COMPLETED, pageSpecs);
		assertNotEquals(eavrops, null);
//		log.debug("COMPLETED: "+ Long.toString(eavrops.getTotalElements()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
			assertEquals(eavrop.getMottagarenOrganisation(),this.fmuListService.findLandstingByLandstingCode(new LandstingCode(currentUser.getLandstingCode())).getName());
			assertTrue(eavrop.getStatus() == EavropStateType.CLOSED
					|| eavrop.getStatus() == EavropStateType.APPROVED);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
    }

    @Test
    public void serviceShouldReturnAllEavropsForUserUtredare() {
    	User currentUser = this.currentUserService.getCurrentUser();
    	currentUser.setActiveRole(Role.ROLE_UTREDARE);
		DateTime fromDate = new DateTime(1990, 1, 1, 0, 0);
		DateTime toDate = new DateTime(2052,12,1, 0, 0);
		Pageable pageSpecs = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "eavropId"));
		
		// NOT ACCEPTED
		EavropPageDTO eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.NOT_ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("NOT ACCEPTED: " + Long.toString(eavrops.getEavrops().size()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		DateTime previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
//			assertTrue(eavrop.get);
			assertTrue(eavrop.getStatus() == EavropStateType.UNASSIGNED
					|| eavrop.getStatus() == EavropStateType.ASSIGNED);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
		
		// ACCEPTED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.ACCEPTED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("ACCEPTED: " + Long.toString(eavrops.getEavrops().size()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
//			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.ACCEPTED
					|| eavrop.getStatus() == EavropStateType.ON_HOLD);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
		
		// COMPLETED
		eavrops = this.fmuOrderingService.getOverviewEavrops(fromDate.getMillis(), toDate.getMillis(),OverviewEavropStates.COMPLETED, pageSpecs);
		assertNotEquals(eavrops, null);
		log.debug("COMPLETED: "+ Long.toString(eavrops.getEavrops().size()));
		assertTrue(eavrops.getEavrops().size() > 0);
		
		previous = null;
		for (EavropDTO eavrop : eavrops.getEavrops()) {
//			assertTrue(eavrop.getLandsting().getLandstingCode().getCode() == currentUser.getLandstingCode());
			assertTrue(eavrop.getStatus() == EavropStateType.CLOSED
					|| eavrop.getStatus() == EavropStateType.APPROVED);
			if(previous != null){
				assertTrue(eavrop.getCreationTime() > previous.getMillis());
			}
		}
    }
}
