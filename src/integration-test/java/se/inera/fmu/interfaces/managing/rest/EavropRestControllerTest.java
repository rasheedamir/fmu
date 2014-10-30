package se.inera.fmu.interfaces.managing.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import se.inera.fmu.Application;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.EavropRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
public class EavropRestControllerTest {
	@Inject
	private FmuOrderingService fmuOrderingService;
	
	@Inject
	private EavropRepository eavropRepo;
	
	private MockMvc restMock;
	
	@Before
	public void SetUp(){
        EavropResource eavropResource = new EavropResource();
        ReflectionTestUtils.setField(eavropResource, "eavropRepository", eavropRepo);
        ReflectionTestUtils.setField(eavropResource, "fmuOrderingService", fmuOrderingService);
        this.restMock = MockMvcBuilders.standaloneSetup(eavropResource).build();
	}
	
	@Test
	public void dummyTest() throws Exception{
		MvcResult result = restMock.perform(get("/app/rest/eavrop")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
}
