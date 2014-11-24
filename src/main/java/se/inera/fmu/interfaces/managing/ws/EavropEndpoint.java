package se.inera.fmu.interfaces.managing.ws;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Interpreter;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import ws.inera.fmu.admin.eavrop.CreateEavropRequest;
import ws.inera.fmu.admin.eavrop.FmuResponse;

/**
 * Created by Rasheed on 10/25/14.
 */
@SuppressWarnings("ALL")
@Endpoint
@Slf4j
public class EavropEndpoint {

    private static final String NAMESPACE_URI = "http://inera.ws/fmu/admin/eavrop";

    @Inject
    private FmuOrderingService fmuOrderingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEavropRequest")
    @ResponsePayload
    public FmuResponse createEavrop(@RequestPayload CreateEavropRequest request) {

        FmuResponse fmuResponse = new FmuResponse();

        try {
            CreateEavropCommand aCommand = mapCreateEavropRequestToCreateEavropCommand(request);
            fmuOrderingService.createEavrop(aCommand);

            fmuResponse.setCode("SUCCESS");
            fmuResponse.setMessage("Request was successfull");
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setCode("FAILED");
            fmuResponse.setCode("Exception occured!");
        }

        return fmuResponse;
    }

    /**
     *
     * @param request - CreateEavropRequest
     * @return
     */
    private CreateEavropCommand mapCreateEavropRequestToCreateEavropCommand(CreateEavropRequest request) {
        ArendeId arendeId = new ArendeId(request.getArendeId());
        UtredningType utredningType = UtredningType.valueOf(request.getUtredningType().toString());
        Interpreter interpreter = createInterpreter(request);
        String description = request.getBeskrivning();
        String utredningFocus = request.getUtredningFokus();
        String additionalInformation = request.getYtterligareInformation();
        Invanare invanare = createInvanare(request);
        LandstingCode landstingCode = new LandstingCode(request.getLandstingId());
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(request);
        PriorMedicalExamination priorMedicalExamination = createPriorMedicalExamination(request);
        return new CreateEavropCommand(arendeId, utredningType, description, utredningFocus, additionalInformation,
                                       interpreter, invanare, landstingCode, bestallaradministrator,
                                       priorMedicalExamination);
    }

    /**
     * Finds if tolk is required then it creates interpreter
     *
     * @param request
     * @return
     */
    private Interpreter createInterpreter(CreateEavropRequest request) {
        String interpreterLanguages = null;
        if(request.isTolk()) {
            interpreterLanguages = request.getTolkSprak();
        }
        return new Interpreter(interpreterLanguages);
    }

    /**
     * Creates invanare from given request
     *
     * @param request
     * @return
     */
    private Invanare createInvanare(CreateEavropRequest request) {
        PersonalNumber personalNumber = new PersonalNumber(request.getInvanare().getPersonalNumber());
        Name name = new Name(request.getInvanare().getNamn().getFornamn(),
                             request.getInvanare().getNamn().getMellannamn(),
                             request.getInvanare().getNamn().getEfternamn());
        Gender gender = Gender.valueOf(request.getInvanare().getGender().toString());
        Address homeAddress = new Address(request.getInvanare().getAdress().getPostadress(),
                                          request.getInvanare().getAdress().getPostnummer(),
                                          request.getInvanare().getAdress().getStad(),
                                          request.getInvanare().getAdress().getLand());
        String phone = request.getInvanare().getTelefon();
        String email = request.getInvanare().getEpost();
        String specialNeeds = request.getInvanare().getSarskildaBehov();
        return new Invanare(personalNumber, name, gender, homeAddress, phone, email, specialNeeds);
    }

    /**
     * Creates bestallaradministrator from given request
     *
     * @param request
     * @return
     */
    private Bestallaradministrator createBestallaradministrator(CreateEavropRequest request) {
        String name = request.getAdministrator().getNamn();
        String befattning = request.getAdministrator().getBefattning();
        String organisation = request.getAdministrator().getOrganisation();
        String unit = request.getAdministrator().getEnhet();
        String phone = request.getAdministrator().getTelefon();
        String email = request.getAdministrator().getEpost();
        return new Bestallaradministrator(name, befattning, organisation, unit, phone, email);
    }

    /**
     * Creates priorMedicalExamination from given request
     *
     * @param request
     * @return
     */
    private PriorMedicalExamination createPriorMedicalExamination(CreateEavropRequest request) {
        String examinedAt = request.getTidigareUtredning().getUtreddVid();
        String medicalLeaveIssuedAt = request.getTidigareUtredning().getSjukskrivandeenhet();
        HoSPerson medicalLeaveIssuedBy = new HoSPerson(null, request.getTidigareUtredning().getSjukskrivenAv().getNamn(),
                request.getTidigareUtredning().getSjukskrivenAv().getBefattning(),
                request.getTidigareUtredning().getSjukskrivenAv().getOrganisation(),
                request.getTidigareUtredning().getSjukskrivenAv().getEnhet());
        return new PriorMedicalExamination(examinedAt, medicalLeaveIssuedAt, medicalLeaveIssuedBy);
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
