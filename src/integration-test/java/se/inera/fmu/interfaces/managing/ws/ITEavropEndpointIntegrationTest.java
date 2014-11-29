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
public class ITEavropEndpointIntegrationTest {

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
    public void createEavrop_BothValidAndInvalidRequests_GeneratesSuccessAndErrorResponse() throws Exception {

    	ArendeId arendeId = new ArendeId("CREATE_TEST");
    	LandstingCode landstingCode = new LandstingCode(1);
    	
    	//test valid request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        validateEavrop(arendeId, landstingCode);
        
        //test invalid request by posting the same request again 
        requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source errorResponsePayload = new StreamSource(createCreateEavropErrorResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(errorResponsePayload));

    }
    

    @Test
    public void createSendDocumentsRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("SEND_DOCUMENT_TEST");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
    }
 
    @Test
    public void bookingDeviationsRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("BOOKING_DEVIATION_RESPONSE_TEST");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Add a booking to the eavrop
        BookingId bookingId = addBookingToEavrop(arendeId);
        //Use Booking to put Eavrop OnHold
        deviateBookingPutEavropOnHold(arendeId, bookingId);
        
        //Receive booking deviation response
        requestPayload = new StreamSource(createBookingDeviationResponseRequest(arendeId,bookingId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

    }

    
    @Test
    public void sentIntygInformationRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("SENT_INTYG_INFORMATION");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg that have been sent
        requestPayload = new StreamSource(createIntygSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

    }

    @Test
    public void intygComplementInformationRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("INTYG_COMPLEMENT_INFORMATION");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg that have been sent
        requestPayload = new StreamSource(createIntygSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg need complements 
        requestPayload = new StreamSource(createIntygComplementRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        
    }

    @Test
    public void intygApprovedInformationRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("INTYG_APPROVED_INFORMATION");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg that have been sent
        requestPayload = new StreamSource(createIntygSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg having been approved 
        requestPayload = new StreamSource(createIntygApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        
    }

    
    @Test
    public void eavropApprovedInformationRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("EAVROP_APPROVED_INFORMATION");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg that have been sent
        requestPayload = new StreamSource(createIntygSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg having been approved 
        requestPayload = new StreamSource(createIntygApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Eavrop have been approved 
        requestPayload = new StreamSource(createEavropApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
    }

    
    @Test
    public void eavropCompensationApprovedInformationRequest() throws Exception {

    	ArendeId arendeId = new ArendeId("EAVROP_COMPENSATION_APPROVED_INFORMATION");
    	LandstingCode landstingCode = new LandstingCode(1);

    	//Receive the eavrop request
        Source requestPayload = new StreamSource(createCreateEavropRequest(arendeId, landstingCode));
        Source succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //Assigning the eavrop
        this.assignEavrop(arendeId, landstingCode);

        //Accepting the eavrop
        this.acceptEavrop(arendeId);
        
        //Receive documents sent request
        requestPayload = new StreamSource(createFMUDocumentsSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg that have been sent
        requestPayload = new StreamSource(createIntygSentRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Receive information about intyg having been approved 
        requestPayload = new StreamSource(createIntygApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Eavrop have been approved 
        requestPayload = new StreamSource(createEavropApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));

        //Eavrop compensation been approved 
        requestPayload = new StreamSource(createEavropCompensationApprovedRequest(arendeId, DateTime.now()));
        succesResponsePayload = new StreamSource(createFMUSuccesResponse(arendeId));
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
    }

    
    
//    private ArendeId getArendeId(String xmlFileName){
//        
//    	try{
//        	JAXBContext context = JAXBContext.newInstance(SkapaFmuEavropRequest.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            
//            StreamSource requestPayload = new StreamSource(new ClassPathResource(xmlFileName).getInputStream());
//            
//            JAXBElement<SkapaFmuEavropRequest> element = unmarshaller.unmarshal(requestPayload,	SkapaFmuEavropRequest.class);
//            SkapaFmuEavropRequest skapaEavropRequest = element.getValue();
//            
//            if(!StringUtils.isBlankOrNull(skapaEavropRequest.getArendeId())){
//            	return new ArendeId(skapaEavropRequest.getArendeId());
//            }
//            
//    	}catch(Exception e){
//    		System.out.println("Exception:" + e.getMessage());
//    		
//    	}
//    	return null;
//    }

    private void validateEavrop(ArendeId arendeId, LandstingCode landstingCode){
    	SkapaFmuEavropRequest eavropRequest =  getSkapaFmuEavropRequest(arendeId, landstingCode);
    	assertNotEquals(eavropRequest, null);
    	Eavrop eavrop = fmuListService.findByArendeId(arendeId);
    	assertNotEquals(eavrop, null);
    	
    	assertEquals(eavrop.getArendeId().toString(), eavropRequest.getArendeId());
    	assertEquals(eavrop.getAdditionalInformation().toString(), eavropRequest.getYtterligareInformation());
    	assertEquals(eavrop.getDescription().toString(), eavropRequest.getBeskrivning());

    	//TODO:
    	
    	
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
		eavrop = fmuListService.findByArendeId(arendeId);
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

    private InputStream createCreateEavropErrorResponse(ArendeId arendeId){
    	String templateFilename = "ws/TST-CREATE_EAVROP-ERROR-RESPONSE_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());
    	
    	return substitute(templateFilename, valueMap);
    }

    
    
    private InputStream createFMUSuccesResponse(ArendeId arendeId){
    	String templateFilename = "ws/TST-FMU-SUCCESS-RESPONSE_TEMPLATE.xml";

    	final Map<String, String> valueMap = new HashMap<String, String>();
    	valueMap.put("ARENDE_ID", arendeId.toString());		
    	
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
