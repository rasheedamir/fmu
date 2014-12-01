package se.inera.fmu.interfaces.managing.ws;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import se.inera.fmu.application.EavropApprovalService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.EavropDocumentService;
import se.inera.fmu.application.EavropIntygService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Interpreter;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.Bestallarsamordnare;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import ws.inera.fmu.admin.eavrop.*;

import static se.inera.fmu.application.util.StringUtils.isBlankOrNull;

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
    @Inject
    private EavropDocumentService eavropDocumentService;
    @Inject
    private EavropBookingService eavropBookingService;
    @Inject
    private EavropIntygService eavropIntygService;
    @Inject
    private EavropApprovalService eavropApprovalService;

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "skapaFmuEavropRequest")
    @ResponsePayload
    public SkapaFmuEavropResponse createEavrop(@RequestPayload SkapaFmuEavropRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        SkapaFmuEavropResponse skapaFmuEavropResponse = new SkapaFmuEavropResponse();

        try {
            CreateEavropCommand aCommand = mapSkapaFmuEavropRequestToCreateEavropCommand(request);
            ArendeId arendeId = fmuOrderingService.createEavrop(aCommand);
            fmuResponse.setArendeId(arendeId.toString());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        skapaFmuEavropResponse.setFmuResponse(fmuResponse);

        return skapaFmuEavropResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "skickatFmuHandlingarRequest")
    @ResponsePayload
    public SkickatFmuHandlingarResponse documentsSent(@RequestPayload SkickatFmuHandlingarRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        SkickatFmuHandlingarResponse skickatFmuHandlingarResponse = new SkickatFmuHandlingarResponse();

        try {
        	AddReceivedExternalDocumentsCommand aCommand = mapSkickatFmuHandlingarRequestToAddReceivedExternalDocumentCommand(request);
            eavropDocumentService.addReceivedExternalDocument(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        skickatFmuHandlingarResponse.setFmuResponse(fmuResponse);

        return skickatFmuHandlingarResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "svarBokningsavvikelseRequest")
    @ResponsePayload
    public SvarBokningsavvikelseResponse bookingDeviationResponse(@RequestPayload SvarBokningsavvikelseRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        SvarBokningsavvikelseResponse svarBokningsavvikelseResponse = new SvarBokningsavvikelseResponse();

        try {
        	AddBookingDeviationResponseCommand aCommand = mapSvarBokningsavvikelseRequestToAddBookingDeviationResponseCommand (request);
            eavropBookingService.addBookingDeviationResponse(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        svarBokningsavvikelseResponse.setFmuResponse(fmuResponse);

        return svarBokningsavvikelseResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "skickatFmuIntygRequest")
    @ResponsePayload
    public SkickatFmuHandlingarResponse intygSent(@RequestPayload SkickatFmuIntygRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        SkickatFmuHandlingarResponse skickatFmuHandlingarResponse = new SkickatFmuHandlingarResponse();

        try {
        	AddIntygSentCommand aCommand = mapSkickatFmuIntygRequestToAddIntygSentCommand (request);
            eavropIntygService.addIntygSentInformation(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        skickatFmuHandlingarResponse.setFmuResponse(fmuResponse);

        return skickatFmuHandlingarResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "begartFmuIntygKompletteringRequest")
    @ResponsePayload
    public BegarKompletteringFmuHandlingResponse intygCompletionRequest(@RequestPayload BegartFmuIntygKompletteringRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        BegarKompletteringFmuHandlingResponse begarKompletteringFmuHandlingResponse = new BegarKompletteringFmuHandlingResponse();

        try {
        	AddIntygComplementRequestCommand aCommand = mapBegartFmuIntygKompletteringRequestToAddIntygComplementRequestCommand (request);
            eavropIntygService.addIntygComplementRequestInformation(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        begarKompletteringFmuHandlingResponse.setFmuResponse(fmuResponse);

        return begarKompletteringFmuHandlingResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "accepteratFmuIntygRequest")
    @ResponsePayload
    public AccepteratFmuIntygResponse intygApproved(@RequestPayload AccepteratFmuIntygRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        AccepteratFmuIntygResponse accepteratFmuIntygResponse = new AccepteratFmuIntygResponse();

        try {
        	AddIntygApprovedCommand aCommand = mapAccepteratFmuIntygRequestToAddIntygApprovedCommand(request);
            eavropIntygService.addIntygApprovedInformation(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        accepteratFmuIntygResponse.setFmuResponse(fmuResponse);

        return accepteratFmuIntygResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "accepteratFmuUtredningRequest")
    @ResponsePayload
    public AccepteratFmuUtredningResponse approveEavrop(@RequestPayload AccepteratFmuUtredningRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        AccepteratFmuUtredningResponse accepteratFmuUtredningResponse = new AccepteratFmuUtredningResponse();

        try {
        	ApproveEavropCommand aCommand = mapAccepteratFmuUtredningRequestToApproveEavropCommand(request);
            eavropApprovalService.approveEavrop(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        accepteratFmuUtredningResponse.setFmuResponse(fmuResponse);

        return accepteratFmuUtredningResponse;
    }

    /**
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "godkannandeErsattningFmuUtredningRequest")
    @ResponsePayload
    public GodkannandeErsattningFmuUtredningResponse approveEavropCompensation(@RequestPayload GodkannandeErsattningFmuUtredningRequest request) {

        FmuResponse fmuResponse = new FmuResponse();
        GodkannandeErsattningFmuUtredningResponse godkannandeErsattningFmuUtredningResponse = new GodkannandeErsattningFmuUtredningResponse();

        try {
        	ApproveEavropCompensationCommand aCommand = mapGodkannandeErsattningFmuUtredningRequestToApproveEavropCompensationCommand(request);
            eavropApprovalService.approveEavropCompensation(aCommand);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.OK);
        } catch (IllegalArgumentException exception) {
            log.error("IllegalArgumentException occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.INVALID_REQUEST);
            fmuResponse.setErrorMessage(exception.toString());
        } catch (Exception exception) {
            log.error("Exception occured : ", exception);
            fmuResponse.setArendeId(request.getArendeId());
            fmuResponse.setStatusCode(StatusCode.UNKNOWN_ERROR);
            fmuResponse.setErrorMessage(exception.toString());
        }

        godkannandeErsattningFmuUtredningResponse.setFmuResponse(fmuResponse);

        return godkannandeErsattningFmuUtredningResponse;
    }
    
	/**
     *
     * @param request - CreateEavropRequest
     * @return
     */
    private CreateEavropCommand mapSkapaFmuEavropRequestToCreateEavropCommand(SkapaFmuEavropRequest request) {
        ArendeId arendeId = new ArendeId(request.getArendeId());
        UtredningType utredningType = UtredningType.valueOf(request.getUtredningType().toString());
        Interpreter interpreter = createInterpreter(request);
        String description = request.getBeskrivning();
        String utredningFocus = request.getUtredningFokus();
        String additionalInformation = request.getYtterligareInformation();
        Invanare invanare = createInvanare(request);
        LandstingCode landstingCode = new LandstingCode(request.getLandstingId());
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(request.getAdministrator());
        PriorMedicalExamination priorMedicalExamination = createPriorMedicalExamination(request);
        return new CreateEavropCommand(arendeId, utredningType, description, utredningFocus, additionalInformation,
                                       interpreter, invanare, landstingCode, bestallaradministrator,
                                       priorMedicalExamination);
    }

	/**
    *
    * @param request - SkickatFmuHandlingarRequest
    * @return
    */
    private AddReceivedExternalDocumentsCommand mapSkickatFmuHandlingarRequestToAddReceivedExternalDocumentCommand(
			SkickatFmuHandlingarRequest request) {
    	ArendeId arendeId = new ArendeId(request.getArendeId());
    	DateTime documentsSentDateTime = new DateTime(request.getHandlingarSkickadeDateTime().toGregorianCalendar().getTimeInMillis());
    	List<String> documents = request.getHandlingar();
    	Bestallaradministrator bestallaradministrator = createBestallaradministrator(request.getHandlingarSkickadeAv());
        
    	return new AddReceivedExternalDocumentsCommand(arendeId, documentsSentDateTime, documents, bestallaradministrator);
	}


	/**
    *
    * @param request - SvarBokningsavvikelseRequest
    * @return
    */
	private AddBookingDeviationResponseCommand mapSvarBokningsavvikelseRequestToAddBookingDeviationResponseCommand(
			SvarBokningsavvikelseRequest request) {
    	ArendeId arendeId = new ArendeId(request.getArendeId());
		BookingId bookingId = new BookingId(request.getBokningsId());
		BookingDeviationResponseType responseType = BookingDeviationResponseType.valueOf(request.getSvar().toString());
		DateTime responseDateTime = new DateTime(request.getSvarDateTime().toGregorianCalendar().getTimeInMillis());
		String note = null;
		Bestallaradministrator administrator = null;		
		if(request.getNotering()!=null && !isBlankOrNull(request.getNotering().getNotering())){
			note = request.getNotering().getNotering();
			administrator = createBestallaradministrator(request.getNotering().getNoteradAv());
		}
		
		return new AddBookingDeviationResponseCommand(arendeId, bookingId, responseType, responseDateTime, note, administrator);
	}


	/**
    *
    * @param request - SkickatFmuIntygRequest
    * @return
    */
	private AddIntygSentCommand mapSkickatFmuIntygRequestToAddIntygSentCommand(
			SkickatFmuIntygRequest request) {
		ArendeId arendeId = new ArendeId(request.getArendeId());
		DateTime intygSentDateTime = new DateTime(request.getIntygSkickatDateTime().toGregorianCalendar().getTimeInMillis());
		HoSPerson hoSPerson = createHoSPerson(request.getIntygSkickatAv());
		return new AddIntygSentCommand(arendeId,intygSentDateTime,hoSPerson);
	}

	/**
     *
    * @param request - BegartFmuIntygKompletteringRequest
    * @return
     */
	private AddIntygComplementRequestCommand mapBegartFmuIntygKompletteringRequestToAddIntygComplementRequestCommand(
			BegartFmuIntygKompletteringRequest request) {
		ArendeId arendeId = new ArendeId(request.getArendeId());
		DateTime intygComplementRequestDateTime = new DateTime(request.getIntygKompletteringBegardDateTime().toGregorianCalendar().getTimeInMillis());
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(request.getIntygKompletteringBegardAv());

		return new AddIntygComplementRequestCommand(arendeId, intygComplementRequestDateTime,bestallaradministrator);
	}

	/**
    *
    * @param request - AccepteratFmuIntygRequest
    * @return
    */
	private AddIntygApprovedCommand mapAccepteratFmuIntygRequestToAddIntygApprovedCommand(
			AccepteratFmuIntygRequest request) {
		ArendeId arendeId = new ArendeId(request.getArendeId());
		DateTime intygApprovedDateTime = new DateTime(request.getIntygAccepteratDateTime().toGregorianCalendar().getTimeInMillis());
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(request.getIntygAccepteratAv());
		return new AddIntygApprovedCommand(arendeId, intygApprovedDateTime, bestallaradministrator);
	}

	/**
    *
    * @param request - AccepteratFmuUtredningRequest
    * @return
    */
	private ApproveEavropCommand mapAccepteratFmuUtredningRequestToApproveEavropCommand(
			AccepteratFmuUtredningRequest request) {
		ArendeId arendeId = new ArendeId(request.getArendeId());
		DateTime eavropApprovedDateTime = new DateTime(request.getUtredningAccepteradDateTime().toGregorianCalendar().getTimeInMillis());
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(request.getUtredningAccepteradAv());
		String note = null;
		Bestallaradministrator administrator = null;		
		if(request.getNotering()!=null && !isBlankOrNull(request.getNotering().getNotering())){
			note = request.getNotering().getNotering();
		}

		return new ApproveEavropCommand(arendeId, eavropApprovedDateTime, bestallaradministrator, note);
	}

	/**
    *
    * @param request - GodkannandeErsattningFmuUtredningRequest
    * @return
    */
	private ApproveEavropCompensationCommand mapGodkannandeErsattningFmuUtredningRequestToApproveEavropCompensationCommand(
			GodkannandeErsattningFmuUtredningRequest request) {
		ArendeId arendeId = new ArendeId(request.getArendeId());
		Boolean isApproved = request.isGodkand();
		DateTime eavropCompensationApprovedDateTime = new DateTime(request.getUtredningErsattningGodkandDateTime().toGregorianCalendar().getTimeInMillis());
        Bestallarsamordnare bestallarsamordnare = createBestallarsamordnare(request.getUtredningErsattningGodkandAv());
		String note = null;
		Bestallaradministrator administrator = null;		
		if(request.getNotering()!=null && !isBlankOrNull(request.getNotering().getNotering())){
			note = request.getNotering().getNotering();
		}
		return new ApproveEavropCompensationCommand(arendeId, eavropCompensationApprovedDateTime, isApproved, bestallarsamordnare, note) ;
	}

	
	
	/**
     * Finds if tolk is required then it creates interpreter
     *
     * @param request
     * @return
     */
    private Interpreter createInterpreter(SkapaFmuEavropRequest request) {
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
    private Invanare createInvanare(SkapaFmuEavropRequest request) {
        PersonalNumber personalNumber = new PersonalNumber(request.getInvanare().getPersonnummer());
        Name name = new Name(request.getInvanare().getNamn().getFornamn(),
                             request.getInvanare().getNamn().getMellannamn(),
                             request.getInvanare().getNamn().getEfternamn());
        Gender gender = Gender.valueOf(request.getInvanare().getKon().toString());
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
    private Bestallaradministrator createBestallaradministrator(Person person) {
        if(person!=null){
        	String name = person.getNamn();
            String befattning = person.getBefattning();
            String organisation = person.getOrganisation();
            String unit = person.getEnhet();
            String phone = person.getTelefon();
            String email = person.getEpost();
            return new Bestallaradministrator(name, befattning, organisation, unit, phone, email);
        	
        }
        return null;
    }


    /**
     * Creates bestallarsamordnare from given request
     *
     * @param request
     * @return
     */
    private Bestallarsamordnare createBestallarsamordnare(Person person) {
        if(person!=null){
        	String name = person.getNamn();
            String befattning = person.getBefattning();
            String organisation = person.getOrganisation();
            String unit = person.getEnhet();
            String phone = person.getTelefon();
            String email = person.getEpost();
            return new Bestallarsamordnare(name, befattning, organisation, unit, phone, email);
        	
        }
        return null;
    }

    
    /**
     * Creates HoSPerson from given request
     *
     * @param request
     * @return
     */
    private HoSPerson createHoSPerson(ws.inera.fmu.admin.eavrop.HoSPerson person) {
        if(person!=null){
        	HsaId hsaId = (isBlankOrNull(person.getHsaId()))?new HsaId(person.getHsaId()):null;
        	String name = person.getNamn();
            String befattning = person.getBefattning();
            String organisation = person.getOrganisation();
            String unit = person.getEnhet();
            String phone = person.getTelefon();
            String email = person.getEpost();
            return new HoSPerson(hsaId, name, befattning, organisation, unit, phone, email);
        	
        }
        return null;
    }

    /**
     * Creates priorMedicalExamination from given request
     *
     * @param request
     * @return
     */
    private PriorMedicalExamination createPriorMedicalExamination(SkapaFmuEavropRequest request) {
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
