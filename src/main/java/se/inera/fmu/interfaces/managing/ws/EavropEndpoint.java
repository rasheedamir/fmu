package se.inera.fmu.interfaces.managing.ws;

import javax.inject.Inject;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import se.inera.fmu.application.FmuOrderingService;
import ws.inera.fmu.admin.eavrop.CreateEavropRequest;
import ws.inera.fmu.admin.eavrop.CreateEavropResponse;

/**
 * Created by Rasheed on 10/25/14.
 */
@SuppressWarnings("ALL")
@Endpoint
public class EavropEndpoint {

    private static final String NAMESPACE_URI = "http://inera.ws/fmu/admin/eavrop";

    @Inject
    private FmuOrderingService fmuOrderingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEavropRequest")
    @ResponsePayload
    public CreateEavropResponse createEavrop(@RequestPayload CreateEavropRequest request) {

        return null;
    }

    //handlingarSkickade or initialDocumentsSent
    //kompletteringSkickade or complimentedDocumentsSent
    //beslutFortsättning
    //notifieraUtredningSkickad
    //notifieraOmBegärdKomplettering
    //notifieraKompletteringSkickad
    //utredningAccepterad
    //ersättning godkänns rad för rad, en i taget or flera åt gången
}
