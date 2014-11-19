package se.inera.fmu.interfaces.managing.ws;


import javax.inject.Inject;
import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;
import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;
/**
 * Created by Rasheed on 11/17/14.
 */
@SuppressWarnings("ALL")
public class ITEavropEndpointIntegrationTest {

    @Inject
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    //https://github.com/kamaydeo/spring-ws-sample/blob/4a912d2e45c4af2d10081575f040480f8df7b71c/src/test/java/com/hbo/endpoint/EmployeeEndpointTest.java

    @Test
    public void createEavrop_IfRequestIsValid_GeneratesSuccessResponse() {

        //Source requestPayload = new StreamSource(new ClassPathResource("Eavrop.xml").getInputStream());

        String createEavropRequest = "";
        String createEavropResponse = "";

        Source requestPayload = new StringSource(createEavropRequest);
        Source responsePayload = new StringSource(createEavropResponse);

        mockClient.sendRequest(withPayload(requestPayload)).
        andExpect(payload(responsePayload));
    }

    @Test
    public void createEavrop_IfRequestIsInValid_GeneratesErrorResponse() {

    }

    @Test
    public void createEavrop_IfExceptionOccursDuringProcessing_GeneratesErrorResponse() {

    }
}
