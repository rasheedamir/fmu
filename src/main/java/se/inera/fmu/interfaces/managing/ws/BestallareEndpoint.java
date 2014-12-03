package se.inera.fmu.interfaces.managing.ws;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ws.fk.fmu.admin.eavrop.*;

/**
 * Created by Rasheed on 12/3/14.
 */
@SuppressWarnings("ALL")
@Endpoint
@Slf4j
public class BestallareEndpoint {

    private static final String NAMESPACE_URI = "http://fk.ws/fmu/admin/eavrop";

    /**
     * Handle's fmuVardgivarenhetTilldelningRequest
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fmuVardgivarenhetTilldelningRequest")
    @ResponsePayload
    public FmuVardgivarenhetTilldelningResponse handleFmuVardgivarenhetTilldelningRequest(FmuVardgivarenhetTilldelningRequest request) {

        log.info(ReflectionToStringBuilder.toString(request));
        ServiceResponseType serviceResponseType = new ServiceResponseType();
        serviceResponseType.setArendeId(request.getArendeId());
        serviceResponseType.setStatusCode(StatusCode.OK);
        FmuVardgivarenhetTilldelningResponse fmuVardgivarenhetTilldelningResponse = new FmuVardgivarenhetTilldelningResponse();
        fmuVardgivarenhetTilldelningResponse.setServiceResponse(serviceResponseType);

        return fmuVardgivarenhetTilldelningResponse;
    }

    /**
     * Handle's begarKompletteringFmuHandlingRequest
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "begarKompletteringFmuHandlingRequest")
    @ResponsePayload
    public BegarKompletteringFmuHandlingResponse handleBegarKompletteringFmuHandlingRequest(BegarKompletteringFmuHandlingRequest request) {

        log.info(ReflectionToStringBuilder.toString(request));
        ServiceResponseType serviceResponseType = new ServiceResponseType();
        serviceResponseType.setArendeId(request.getArendeId());
        serviceResponseType.setStatusCode(StatusCode.OK);
        BegarKompletteringFmuHandlingResponse begarKompletteringFmuHandlingResponse = new BegarKompletteringFmuHandlingResponse();
        begarKompletteringFmuHandlingResponse.setServiceResponse(serviceResponseType);

        return begarKompletteringFmuHandlingResponse;
    }

    /**
     * Handle's fmuBokningRequest
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fmuBokningRequest")
    @ResponsePayload
    public FmuBokningResponse handleFmuBokningRequest(FmuBokningRequest request) {

        log.info(ReflectionToStringBuilder.toString(request));
        ServiceResponseType serviceResponseType = new ServiceResponseType();
        serviceResponseType.setArendeId(request.getArendeId());
        serviceResponseType.setStatusCode(StatusCode.OK);
        FmuBokningResponse fmuBokningResponse = new FmuBokningResponse();
        fmuBokningResponse.setServiceResponse(serviceResponseType);

        return null;
    }

    /**
     * Handle's fmuBokningsavvikelseRequest
     * @param request
     * @return
     */
    public FmuBokningsavvikelseResponse handleFmuBokningsavvikelseRequest(FmuBokningsavvikelseRequest request) {

        log.info(ReflectionToStringBuilder.toString(request));
        ServiceResponseType serviceResponseType = new ServiceResponseType();
        serviceResponseType.setArendeId(request.getArendeId());
        serviceResponseType.setStatusCode(StatusCode.OK);
        FmuBokningsavvikelseResponse fmuBokningsavvikelseResponse = new FmuBokningsavvikelseResponse();
        fmuBokningsavvikelseResponse.setServiceResponse(serviceResponseType);

        return null;
    }

    /**
     * Handle's fmuIntygSentRequest
     * @param request
     * @return
     */
    public FmuIntygSentResponse handleFmuIntygSentRequest(FmuIntygSentRequest request) {

        log.info(ReflectionToStringBuilder.toString(request));
        ServiceResponseType serviceResponseType = new ServiceResponseType();
        serviceResponseType.setArendeId(request.getArendeId());
        serviceResponseType.setStatusCode(StatusCode.OK);
        FmuIntygSentResponse fmuIntygSentResponse = new FmuIntygSentResponse();
        fmuIntygSentResponse.setServiceResponse(serviceResponseType);

        return null;
    }
}
