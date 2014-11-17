package se.inera.fmu.interfaces.managing.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

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
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TimeDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
// Randomize ports
@Validated
@Slf4j
public class ITEavropRestControllerTest {

	@Inject
	private FmuOrderingService fmuOrderingService;
	private MockMvc restMock;

	@Inject
	private CurrentUserService currentUserService;

	@Before
	public void SetUp() {
		EavropResource eavropResource = new EavropResource();
		ReflectionTestUtils.setField(eavropResource, "fmuOrderingService",
				fmuOrderingService);
		this.restMock = MockMvcBuilders.standaloneSetup(eavropResource).build();
		TestUtil.loginWithNoActiveRole();
	}

	@Test
	public void loggedInasLandstingSamordnareNotAccepted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(
				Role.LANDSTINGSSAMORDNARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/NOT_ACCEPTED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void loggedInasLandstingSamordnareAccepted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(
				Role.LANDSTINGSSAMORDNARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/ACCEPTED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void loggedInasLandstingSamordnareCompleted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(
				Role.LANDSTINGSSAMORDNARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/COMPLETED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void loggedInasUtredareNotAccepted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(Role.UTREDARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/NOT_ACCEPTED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void loggedInasUtredareAccepted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(Role.UTREDARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/ACCEPTED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void loggedInasUtredareCompleted() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(Role.UTREDARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		DateTime startDate = new DateTime(1990, 1, 1, 0, 0, 0);
		DateTime endDate = new DateTime(2990, 1, 1, 0, 0, 0);
		MvcResult result = restMock
				.perform(
						get(
								"/app/rest/eavrop" + "/fromdate/"
										+ startDate.getMillis() + "/todate/"
										+ endDate.getMillis()
										+ "/status/COMPLETED" + "/page/0"
										+ "/pagesize/10" + "/sortkey/arendeId"
										+ "/sortorder/ASC").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void getEavropEvents() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(
				Role.LANDSTINGSSAMORDNARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);

		MvcResult result = restMock
				.perform(
						get("/app/rest/eavrop/1/utredning").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.error(result.getResponse().getContentAsString());
	}

	// TODO Check time POST/GET consistency

	@Test
	public void createBookingTest() throws Exception {
		this.currentUserService.getCurrentUser().setActiveRole(
				Role.LANDSTINGSSAMORDNARE);
		this.currentUserService.getCurrentUser().setLandstingCode(1);
		BookingRequestDTO booking = new BookingRequestDTO();
		booking.setEavropId("3");
		booking.setBookingType(BookingType.EXAMINATION);
		booking.setBookingDate(new DateTime().getMillis());
		booking.setBookingStartTime(new TimeDTO().setHour(12).setMinute(30));
		booking.setBookingEndTime(new TimeDTO().setHour(13).setMinute(30));
		booking.setPersonName("Åsa Andersson");
		booking.setPersonOrganisation("Karolinska Sjukhuset");
		booking.setPersonRole("Läkare");
		booking.setPersonUnit("Unit 3");
		booking.setUseInterpreter(true);

		restMock.perform(
				post("/app/rest/eavrop/utredning/create/booking").contentType(
						MediaType.APPLICATION_JSON).content(
						convertObjectToJsonBytes(booking))).andExpect(
				status().isOk());

		MvcResult result = restMock
				.perform(
						get("/app/rest/eavrop/3/utredning").accept(
								MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		result.getResponse().setCharacterEncoding("UTF-8");
		log.error(result.getResponse().getContentAsString());
	}

	public static byte[] convertObjectToJsonBytes(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
