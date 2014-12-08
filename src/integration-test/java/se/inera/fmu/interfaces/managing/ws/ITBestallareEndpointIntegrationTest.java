package se.inera.fmu.interfaces.managing.ws;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import com.google.common.io.CharStreams;

import se.inera.fmu.Application;
import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import ws.inera.fmu.admin.eavrop.SkapaFmuEavropRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;
/**
 * Created by Rasheed, Rickard on 11/17/14.
 */
@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
@Slf4j
public class ITBestallareEndpointIntegrationTest {
	
	private static final String CREATE_EAVROP_RESPONSE = "skapaFmuEavropResponse";
	private static final String SENT_FMU_DOCUMENTS_RESPONSE = "skickatFmuHandlingarResponse";
	private static final String BOOKING_DEVIATION_RESPONSE_RESPONSE = "svarBokningsavvikelseResponse";
	private static final String SENT_INTYG_RESPONSE = "skickatFmuIntygResponse";
	private static final String INTYG_COMPLEMENT_REQUEST_RESPONSE = "begartFmuIntygKompletteringResponse";
	private static final String INTYG_APPROVED_RESPONSE = "accepteratFmuIntygResponse";
	private static final String EAVROP_APPROVED_RESPONSE = "accepteratFmuUtredningResponse";
	private static final String EAVROP_COMPENSATION_APPROVED_RESPONSE = "godkannandeErsattningFmuUtredningResponse";
	
    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private FmuListService fmuListService;
    
    @Inject
    private EavropAssignmentService eavropAssignmentService;

    @Inject
    private EavropBookingService eavropBookingService;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    	


	@Test
    public void acceptAssignment() throws Exception {

    	ArendeId arendeId = new ArendeId("150000000001");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(CREATE_EAVROP_RESPONSE, arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(SENT_FMU_DOCUMENTS_RESPONSE,arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
    }
 

    private void validateEavrop(ArendeId arendeId, LandstingCode landstingCode){
    	SkapaFmuEavropRequest eavropRequest =  getSkapaFmuEavropRequest(arendeId, landstingCode);
    	assertNotEquals(eavropRequest, null);
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
    	
    	assertEquals(eavropRequest.getArendeId(), eavrop.getArendeId().toString());
    	
    	assertEquals(eavropRequest.getAdministrator().getNamn(), eavrop.getBestallaradministrator().getName());
    	assertEquals(eavropRequest.getAdministrator().getBefattning(), eavrop.getBestallaradministrator().getRole());
    	//assertEquals(eavropRequest.getAdministrator().getId(), eavrop.getBestallaradministrator().);
    	assertEquals(eavropRequest.getAdministrator().getEnhet(), eavrop.getBestallaradministrator().getUnit());
    	assertEquals(eavropRequest.getAdministrator().getOrganisation(), eavrop.getBestallaradministrator().getOrganisation());
    	assertEquals(eavropRequest.getAdministrator().getTelefon(), eavrop.getBestallaradministrator().getPhone());
    	assertEquals(eavropRequest.getAdministrator().getEpost(), eavrop.getBestallaradministrator().getEmail());

    	assertEquals(eavropRequest.getBeskrivning(), eavrop.getDescription().toString());

    	assertEquals(eavropRequest.getInvanare().getNamn().getFornamn(), eavrop.getInvanare().getName().getFirstName());
    	assertEquals(eavropRequest.getInvanare().getNamn().getMellannamn(), eavrop.getInvanare().getName().getMiddleName());
    	assertEquals(eavropRequest.getInvanare().getNamn().getEfternamn(), eavrop.getInvanare().getName().getLastName());
    	assertEquals(eavropRequest.getInvanare().getKon().name(), eavrop.getInvanare().getGender().name());
    	assertEquals(eavropRequest.getInvanare().getPersonnummer(), eavrop.getInvanare().getPersonalNumber().getPersonalNumber());
    	assertEquals(eavropRequest.getInvanare().getAdress().getPostadress(), eavrop.getInvanare().getHomeAddress().getAddress1());
    	assertEquals(eavropRequest.getInvanare().getAdress().getPostnummer(), eavrop.getInvanare().getHomeAddress().getPostalCode());
    	assertEquals(eavropRequest.getInvanare().getAdress().getStad(), eavrop.getInvanare().getHomeAddress().getCity());
    	assertEquals(eavropRequest.getInvanare().getAdress().getLand(), eavrop.getInvanare().getHomeAddress().getCountry());
    	assertEquals(eavropRequest.getInvanare().getTelefon(), eavrop.getInvanare().getPhone());
    	assertEquals(eavropRequest.getInvanare().getEpost(), eavrop.getInvanare().getEmail());
    	assertEquals(eavropRequest.getInvanare().getSarskildaBehov(), eavrop.getInvanare().getSpecialNeeds());
    	
    	assertEquals(new LandstingCode(eavropRequest.getLandstingId()), eavrop.getLandsting().getLandstingCode());
      	
    	assertEquals(eavropRequest.getTidigareUtredning().getUtreddVid(), eavrop.getPriorMedicalExamination().getExaminedAt());
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivandeenhet(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedAt());  	
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getNamn(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getName());
    	//assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getId(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().);
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getBefattning(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getRole());
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getEnhet(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getUnit());
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getOrganisation(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getOrganisation());
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getTelefon(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getPhone());
    	assertEquals(eavropRequest.getTidigareUtredning().getSjukskrivenAv().getEpost(), eavrop.getPriorMedicalExamination().getMedicalLeaveIssuedBy().getEmail());

    	assertEquals(eavropRequest.getTolkSprak(), eavrop.getIterpreterDescription());
    	assertEquals(eavropRequest.getUtredningFokus(), eavrop.getUtredningFocus());
    	assertEquals(eavropRequest.getUtredningType().name(), eavrop.getUtredningType().name());
    	assertEquals(eavropRequest.getTolkSprak(), eavrop.getIterpreterDescription());

    	assertEquals(eavropRequest.getYtterligareInformation(), eavrop.getAdditionalInformation().toString());

    	
    	
    }
    
    
    
    private SkapaFmuEavropRequest getSkapaFmuEavropRequest(ArendeId arendeId, LandstingCode landstingCode){
        
    	try{
        	JAXBContext context = JAXBContext.newInstance(SkapaFmuEavropRequest.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StreamSource requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
            
            JAXBElement<SkapaFmuEavropRequest> element = unmarshaller.unmarshal(requestPayload,	SkapaFmuEavropRequest.class);
            SkapaFmuEavropRequest skapaEavropRequest = element.getValue();
            
            return skapaEavropRequest;
            
    	}catch(Exception e){
    		log.error("Exception occured : ", e.getMessage());
    	}
    	return null;
    }

	private void assignEavrop(ArendeId arendeId, LandstingCode landstingCode){
    	log.debug(String.format("Assigning eavrop with ArendeId: %s ", arendeId.toString()));
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
		Landsting landsting =  fmuListService.findLandstingByLandstingCode(landstingCode);
		assertNotEquals(landsting.getVardgivarenheter(), null);
		assertEquals(landsting.getVardgivarenheter().isEmpty(), Boolean.FALSE);
		Vardgivarenhet vardgivarenhet = landsting.getVardgivarenheter().iterator().next();
		
		AssignEavropCommand assign = 
				new AssignEavropCommand(eavrop.getEavropId(), 
						vardgivarenhet.getHsaId(), 
						new HsaId("SE2222223333-BBBB"),"A", "B", "C","D" );
		
		eavropAssignmentService.assignEavropToVardgivarenhet(assign);

		eavrop = fmuListService.findByArendeId(arendeId);
		assertTrue(EavropStateType.ASSIGNED.equals(eavrop.getStatus()));
    }

    private void acceptEavrop(ArendeId arendeId){
    	log.debug(String.format("Accepting eavrop with ArendeId: %s ", arendeId.toString()));
    	
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
    	assertNotEquals(eavrop.getCurrentAssignedVardgivarenhet(), null);
    	assertNotEquals(eavrop.getCurrentAssignedVardgivarenhet().getHsaId(), null);

    	AcceptEavropAssignmentCommand acceptEavropAssignmentCommand = 
    			new AcceptEavropAssignmentCommand(eavrop.getEavropId(),
    					eavrop.getCurrentAssignedVardgivarenhet().getHsaId(),
    					new HsaId("SE2222223333-BBBB"),"A", "B", "C","D");
    	
		eavropAssignmentService.acceptEavropAssignment(acceptEavropAssignmentCommand);
		eavrop = fmuListService.findByArendeId(arendeId);
		assertTrue(EavropStateType.ACCEPTED.equals(eavrop.getStatus()));
    }
    
    private BookingId addBookingToEavrop(ArendeId arendeId){
    	log.debug(String.format("Add booking to eavrop with ArendeId: %s ", arendeId.toString()));
    	
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
    	
    	CreateBookingCommand createBookingCommand = 
    		new CreateBookingCommand(eavrop.getEavropId(),
    			BookingType.EXAMINATION,
    			DateTime.now(),
    			DateTime.now().plusHours(1),"","", true,true);
    	
     	
		eavropBookingService.createBooking(createBookingCommand);
		eavrop = fmuListService.findByArendeIdInitialized(arendeId);
		assertTrue(EavropStateType.ACCEPTED.equals(eavrop.getStatus()));
		assertNotEquals(eavrop.getBookings(), null);
		assertNotEquals(eavrop.getBookings().isEmpty(), true);
		Booking booking = eavrop.getBookings().iterator().next();
		
		return booking.getBookingId();
    }

    private void deviateBookingPutEavropOnHold(ArendeId arendeId,
			BookingId bookingId) {
    	log.debug(String.format("Deviate booking with id: %s on eavrop with ArendeId: %s ", bookingId.toString(), arendeId.toString()));
    	
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
    	
    	ChangeBookingStatusCommand changeBookingStatusCommand = new ChangeBookingStatusCommand(eavrop.getEavropId(),
    			bookingId, BookingStatusType.CANCELLED_NOT_PRESENT, "C",new HsaId("SE2222223333-BBBB"),"A","B","C","D");  
     	
		eavropBookingService.changeBookingStatus(changeBookingStatusCommand);
		eavrop = fmuListService.findByArendeId(arendeId);
		assertTrue(EavropStateType.ON_HOLD.equals(eavrop.getStatus()));

	}

    private InputStream createCreateEavropRequest(ArendeId arendeId, LandstingCode landstingCode){
    	String templateFilename = "ws/TST-CREATE_EAVROP_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("LANDSTING_ID", landstingCode.toString());
    	
    	return substitute(templateFilename, valueMap);
    }

    
    private InputStream createCreateEavropOnlyMandatoryRequest(ArendeId arendeId, LandstingCode landstingCode){
    	String templateFilename = "ws/TST-CREATE_EAVROP_REQUEST_ONLY_MANDATORY.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("LANDSTING_ID", landstingCode.toString());
    	
    	return substitute(templateFilename, valueMap);
    }

    private InputStream createCreateEavropErrorResponse(String responseType, ArendeId arendeId){
    	String templateFilename = "ws/TST-CREATE_EAVROP-ERROR-RESPONSE_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("RESPONSE_TYPE", responseType);
    	
    	return substitute(templateFilename, valueMap);
    }

    
    
    private InputStream createFMUSuccesResponse(String responseType, ArendeId arendeId){
    	String templateFilename = "ws/TST-FMU-SUCCESS-RESPONSE_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());	
    	valueMap.put("RESPONSE_TYPE", responseType);
    	    	
    	return substitute(templateFilename, valueMap);
    }

    private InputStream createFMUDocumentsSentRequest(ArendeId arendeId, DateTime dateTime){
    	String templateFilename = "ws/TST-DOCUMENTS_SENT_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
    }

    private InputStream createBookingDeviationResponseRequest(ArendeId arendeId, BookingId bookingId, DateTime dateTime){
    	String templateFilename = "ws/TST-BOOKING_DEVIATION_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("BOOKNING_ID", bookingId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
    }

	private InputStream createIntygSentRequest(ArendeId arendeId, DateTime dateTime) {
		String templateFilename = "ws/TST-INTYG_SENT_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
	}

    
	private InputStream createIntygComplementRequest(ArendeId arendeId, DateTime dateTime) {
		String templateFilename = "ws/TST-INTYG_COMPLEMENT_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
	}

	private InputStream createIntygApprovedRequest(ArendeId arendeId, DateTime dateTime) {
		String templateFilename = "ws/TST-INTYG_APPROVED_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
	}

	private InputStream createEavropApprovedRequest(ArendeId arendeId, DateTime dateTime) {
		String templateFilename = "ws/TST-EAVROP_APPROVED_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
	}

	private InputStream createEavropCompensationApprovedRequest(
			ArendeId arendeId, DateTime dateTime) {
		String templateFilename = "ws/TST-EAVROP_COMPENSATION_APPROVED_REQUEST_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	valueMap.put("DATE_TIME", DatatypeConverter.printDateTime(dateTime.toGregorianCalendar()));
    	
    	return substitute(templateFilename, valueMap);
	}

    
    private InputStream substitute(String filename, Map<String, String> valueMap){
    	try {
        	String stringFromInputStream = IOUtils.toString(new ClassPathResource(filename).getInputStream(), StandardCharsets.UTF_8);
        	return  new ByteArrayInputStream(StrSubstitutor.replace(stringFromInputStream, valueMap).getBytes(StandardCharsets.UTF_8));
			
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
    	return null;
    }
    
}
