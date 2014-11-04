package se.inera.fmu.interfaces.managing.rest;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import se.inera.fmu.Application;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
public class ITEavropRestControllerTest {
	
	@Inject
	private EavropRepository eavropRepository;
	
	@Inject LandstingRepository landstingRepository;
	
	@Mock
	private FmuOrderingService fmuOrderingService;
	
	@InjectMocks 
	private EavropResource eavropResource;
	
	private MockMvc restMock;
	
	@Before
	public void SetUp(){
		MockitoAnnotations.initMocks(this);
        this.restMock = MockMvcBuilders.standaloneSetup(eavropResource).build();
	}
	
	@Test
	public void dummyTest() throws Exception{
		when(fmuOrderingService.getOverviewEavrops()).thenReturn(getAllEavropsForLandsting1());
		
		MvcResult result = restMock.perform(get("/app/rest/eavrop")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();
	}
	
	private List<Eavrop> getAllEavropsForLandsting1(){
		Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(1));
		List<Eavrop> eavrops = this.eavropRepository.findAllByLandsting(landsting);
		return eavrops;
	}
}
