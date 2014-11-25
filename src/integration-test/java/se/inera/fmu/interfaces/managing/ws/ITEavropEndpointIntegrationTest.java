package se.inera.fmu.interfaces.managing.ws;


import javax.inject.Inject;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

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
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;
import se.inera.fmu.Application;

import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;
/**
 * Created by Rasheed on 11/17/14.
 */
@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
public class ITEavropEndpointIntegrationTest {

    @Inject
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    	

    @Test
    public void createEavrop_BothValidAndInvalidRequests_GeneratesSuccessAndErrorResponse() throws Exception {

    	//test valid request
        Source requestPayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP.xml").getInputStream());
        Source succesResponsePayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP-SUCCESS-RESPONSE.xml").getInputStream());
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //test invalid request by posting the same request again 
        requestPayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP.xml").getInputStream());
        Source errorResponsePayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP-ERROR-RESPONSE.xml").getInputStream());
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(errorResponsePayload));

    }
    

    @Test
    public void createSendDocumentsEavrop_BothValidAndInvalidRequests_GeneratesSuccessAndErrorResponse() throws Exception {

    	//test valid request
        Source requestPayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP.xml").getInputStream());
        Source succesResponsePayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP-SUCCESS-RESPONSE.xml").getInputStream());
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(succesResponsePayload));
        
        //test invalid request by posting the same request again 
        requestPayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP.xml").getInputStream());
        Source errorResponsePayload = new StreamSource(new ClassPathResource("ws/TST-CREATE_EAVROP-ERROR-RESPONSE.xml").getInputStream());
        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(errorResponsePayload));

    }

    

    @Test
    public void createEavrop_IfRequestIsInValid_GeneratesErrorResponse() throws Exception{
    }

    @Test
    public void createEavrop_IfExceptionOccursDuringProcessing_GeneratesErrorResponse() {

    }
}
