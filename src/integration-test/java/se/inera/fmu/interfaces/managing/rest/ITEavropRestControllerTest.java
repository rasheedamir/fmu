package se.inera.fmu.interfaces.managing.rest;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.Application;
import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.authentication.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0") //Randomize ports
@Validated
@Slf4j
public class ITEavropRestControllerTest {
	@Inject
	private ApplicationContext appContext;
	
	@Inject
	private FmuOrderingService fmuOrderingService;
	private MockMvc restMock;
	
	@Inject
	private CurrentUserService currentUserService;
	
	@Before
	public void SetUp(){
        EavropResource eavropResource = new EavropResource();
        ReflectionTestUtils.setField(eavropResource, "fmuOrderingService", fmuOrderingService);
        this.restMock = MockMvcBuilders.standaloneSetup(eavropResource).build();
        TestUtil.loginWithNoActiveRole();
	}
	
	@Test
	public void loggedInasLandstingSamordnare() throws Exception{
		this.currentUserService.getCurrentUser().setActiveRole(Role.LANDSTINGSSAMORDNARE);
		MvcResult result = restMock.perform(get(
				"/app/rest/eavrop/landstingcode/12/fromdate/1/todate/2"
				+ "/page/1/pagesize/10/sortkey/arendeId/sortorder/ASC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
		
		log.debug(result.getResponse().getContentAsString());
	}
	
	@Test
	public void landstingParameterAsStringWillNotPass() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(Role.LANDSTINGSSAMORDNARE);
		restMock.perform(get(
				"/app/rest/eavrop/landstingcode/abc/fromdate/1/todate/2/status/ASSIGNED"
				+ "/page/1/pagesize/10/sortkey/arendeId/sortorder/ASC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
	}
	
	@Test
	public void loggedInAsUtredare() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(Role.UTREDARE);
//		MvcResult result = restMock.perform(get("/app/rest/eavrop")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
	}
}
