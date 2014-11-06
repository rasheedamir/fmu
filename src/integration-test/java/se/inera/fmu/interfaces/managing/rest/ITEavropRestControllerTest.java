package se.inera.fmu.interfaces.managing.rest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
		this.currentUserService.getCurrentUser().setLandstingCode(1);
		
		DateTime startDate = new DateTime(1990,1,1,0,0,0);
		DateTime endDate = new DateTime(2990,1,1,0,0,0);
		MvcResult result = restMock.perform(get(
				"/app/rest/eavrop"
				+ "/fromdate/" + startDate.getMillis()
				+ "/todate/" + endDate.getMillis()
				+"/status/NOT_ACCEPTED"
				+ "/page/0"
				+ "/pagesize/10"
				+ "/sortkey/arendeId"
				+ "/sortorder/ASC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}
}
