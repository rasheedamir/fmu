package se.inera.fmu.application.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import se.inera.fmu.application.FmuEventService;
import se.inera.fmu.application.IncomingCommunicationGeneratorService;
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
import se.inera.fmu.facade.FmuWebServiceFacade;

/**
 * 
 * @see IncomingCommunicationGeneratorService
 *
 */
@Slf4j
@Service
public class IncomingCommunicationGeneratorServiceImpl  implements IncomingCommunicationGeneratorService {

    @Inject
    FmuWebServiceFacade fmuWebServiceFacade;  
	
	@Override
	public ArendeId generateEavrop(){
		CreateEavropCommand eavropCommand = createEavropCommand();
		ArendeId arendeId = fmuWebServiceFacade.createEavrop(eavropCommand);
		log.debug(String.format("Generated Eavrop with ArendeId: %s", arendeId.toString()));
		return arendeId;
	}

	@Override
	public ArendeId generateSentDocuments(ArendeId arendeId){
    	AddReceivedExternalDocumentsCommand documentCommand = createAddReceivedExternalDocumentsCommand(arendeId);
    	fmuWebServiceFacade.addReceivedExternalDocument(documentCommand);
		log.debug(String.format("Generated SentDocuments for Eavrop with ArendeId: %s", arendeId.toString()));
    	return arendeId;
	}
	
	@Override
	public ArendeId generateBookingDeviationResponse(ArendeId arendeId, BookingId bookingId, BookingDeviationResponseType bookingDeviationResponseType){
    	AddBookingDeviationResponseCommand aCommand = createAddBookingDeviationResponseCommand(arendeId, bookingId, bookingDeviationResponseType);
    	fmuWebServiceFacade	.addBookingDeviationResponse(aCommand);
		log.debug(String.format("Generated booking deviation response for Eavrop with ArendeId: %s", arendeId.toString()));
    	return arendeId;
	}

	@Override
	public ArendeId generateIntygSent(ArendeId arendeId) {
    	AddIntygSentCommand aCommand = createAddIntygSentCommand(arendeId);
        fmuWebServiceFacade.addIntygSentInformation(aCommand);
		log.debug(String.format("Generated intyg sent information for Eavrop with ArendeId: %s", arendeId.toString()));
        return arendeId;
    }

	@Override
	public ArendeId generateIntygComplementRequestInformation(ArendeId arendeId){
    	AddIntygComplementRequestCommand aCommand = createAddIntygComplementRequestCommand(arendeId) ;
        fmuWebServiceFacade.addIntygComplementRequestInformation(aCommand);
		log.debug(String.format("Generated intyg completion request for Eavrop with ArendeId: %s", arendeId.toString()));
        return arendeId;
	}

	@Override
	public ArendeId generateIntygApprovedInformation(ArendeId arendeId){
    	AddIntygApprovedCommand aCommand = createAddIntygApprovedCommand(arendeId);
        fmuWebServiceFacade.addIntygApprovedInformation(aCommand);
        log.debug(String.format("Generated intyg approved request for Eavrop with ArendeId: %s", arendeId.toString()));
        return arendeId;
	}
	
	@Override
	public ArendeId generateEavropApproval(ArendeId arendeId){
    	ApproveEavropCommand aCommand = createApproveEavropCommand(arendeId);
        fmuWebServiceFacade.approveEavrop(aCommand);
        log.debug(String.format("Generated eavrop approval for Eavrop with ArendeId: %s", arendeId.toString()));
    	return arendeId;
	}

	@Override
	public ArendeId generateEavropCompensationApproval(ArendeId arendeId, boolean isApproved){
		ApproveEavropCompensationCommand aCommand = createApproveEavropCompensationCommand(arendeId, isApproved);
      	fmuWebServiceFacade.approveEavropCompensation(aCommand);
      	log.debug(String.format("Generated eavrop compensation approval for Eavrop with ArendeId: %s", arendeId.toString()));
    	return arendeId;
	}

	private CreateEavropCommand createEavropCommand() {
        ArendeId arendeId = generateArendeId();
        UtredningType utredningType = randomUtredningType();
        Interpreter interpreter = randomInterpreter();
        String description = getBeskrivning();
        String utredningFocus = getUtredningFokus();
        String additionalInformation = getYtterligareInformation();
        Invanare invanare = createInvanare();
        LandstingCode landstingCode = getLandstingCode();
        Bestallaradministrator bestallaradministrator = createBestallaradministrator();
        PriorMedicalExamination priorMedicalExamination = createPriorMedicalExamination();
        return new CreateEavropCommand(arendeId, utredningType, description, utredningFocus, additionalInformation,
                                       interpreter, invanare, landstingCode, bestallaradministrator,
                                       priorMedicalExamination);
	}

	private static ArendeId generateArendeId() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYMMddkkmmss");
		DateTime dateTime = DateTime.now();
		String arendeIdString = dtf.print(dateTime);
		ArendeId arendeId = new ArendeId(arendeIdString);
		return arendeId;
	}
	
	private UtredningType randomUtredningType(){
		Random random = new Random();
		List<UtredningType> valueList =   Collections.unmodifiableList(Arrays.asList(UtredningType.values()));
		return valueList.get(random.nextInt(valueList.size()));
	}

	private Interpreter randomInterpreter(){
		Random random = new Random();
		Interpreter interpreter = null;
		if(random.nextBoolean()){
			interpreter = new Interpreter("Finlandssvenska");
		}
		return interpreter;
	}

	private String getBeskrivning() {
		return "Valfri beskrivning av ärendet";
	}

	private String getUtredningFokus() {
		return "Psykologi";
	}

	private String getYtterligareInformation() {
		return "Ytterligare information i ärendet";
	}
	
	private LandstingCode getLandstingCode(){
		return new LandstingCode(1);
	}
	
    private Invanare createInvanare() {
        PersonalNumber personalNumber = new PersonalNumber("8112189876");
        Name name = new Name("Anna",
                             null,
                             "Andersson");
        Gender gender = Gender.FEMALE;
        Address homeAddress = new Address("Aspvägen 1",
                                          "12345",
                                          "Astad",
                                          "Sverige");
        String phone = "09 123456";
        String email = "anna.andersson@apost.se";
        String specialNeeds = "Hiss";
        return new Invanare(personalNumber, name, gender, homeAddress, phone, email, specialNeeds);
    }
    
    private Bestallaradministrator createBestallaradministrator() {
    	String name = "Berit Bengtsson";
        String befattning = "Personlig handläggare";
        String organisation = "Försäkringskassan";
        String unit = "Stockholm, Sundbyberg Servicekontor";
        String phone = "08-784321500";
        String email = "berit.bengtsson@forsakringskassan.se";
        return new Bestallaradministrator(name, befattning, organisation, unit, phone, email);
    }

    private Bestallarsamordnare createBestallarsamordnare() {
    	String name = "Ulf Ulvesson";
        String befattning = "Samordnare";
        String organisation = "Försäkringskassan";
        String unit = "Stockholm, Sundbyberg Servicekontor";
        String phone = "08-784321501";
        String email = "ulf.ulvesson@forsakringskassan.se";
        return new Bestallarsamordnare(name, befattning, organisation, unit, phone, email);
    }

    private PriorMedicalExamination createPriorMedicalExamination() {
        String examinedAt = "Rehabkliniken, SLU, 2010-01-25";
        String medicalLeaveIssuedAt = "Vårdcentralen Granen";
        HoSPerson medicalLeaveIssuedBy = null;
	    HsaId hsaId = null;
	    
	    medicalLeaveIssuedBy = new HoSPerson(hsaId, 
	    		"Folke Fransson",
	            "Leg. Läkare",
	            "Vårdbolaget AB",
	            "Vårdcentralen Granen",
	            "08-41268560",
	            "folke.fransson@vardbolaget.se");
	    return new PriorMedicalExamination(examinedAt, medicalLeaveIssuedAt, medicalLeaveIssuedBy);
    }

	private AddReceivedExternalDocumentsCommand createAddReceivedExternalDocumentsCommand(ArendeId arendeId) {
    	DateTime documentsSentDateTime = DateTime.now();
    	List<String> documents = new ArrayList<String>(Arrays.asList("Journal", "SASSAM", "Appendix A"));
    	Bestallaradministrator bestallaradministrator = createBestallaradministrator();
    	return new AddReceivedExternalDocumentsCommand(arendeId, documentsSentDateTime, documents, bestallaradministrator);
	}
	
	private AddBookingDeviationResponseCommand createAddBookingDeviationResponseCommand(ArendeId arendeId, BookingId bookingId, BookingDeviationResponseType responseType){
		DateTime responseDateTime = DateTime.now();
		Bestallaradministrator administrator = createBestallaradministrator();
		String note = (BookingDeviationResponseType.RESTART.equals(responseType))?"Utredningen omstartad":"Utredningen avslutas"; 
		return new AddBookingDeviationResponseCommand(arendeId, bookingId, responseType, responseDateTime, note, administrator);
	}

    private HoSPerson createHoSPerson() {
    	HsaId hsaId = new HsaId("IFV1239877878-104B");
    	String name = "Åsa Andesson";
        String befattning = "Leg. Läkare";
        String organisation = "Stockholms läns landsting";
        String unit = "FMU enheten";
        String phone = "08-12231223";
        String email = "asa.andersson@sll.se";
        return new HoSPerson(hsaId, name, befattning, organisation, unit, phone, email);
    }
    
	private AddIntygSentCommand createAddIntygSentCommand(ArendeId arendeId) {
		DateTime intygSentDateTime = DateTime.now();
		HoSPerson hoSPerson = createHoSPerson();
		return new AddIntygSentCommand(arendeId,intygSentDateTime,hoSPerson);
	}

	private AddIntygComplementRequestCommand createAddIntygComplementRequestCommand(ArendeId arendeId) {
		DateTime intygComplementRequestDateTime = DateTime.now();
        Bestallaradministrator bestallaradministrator = createBestallaradministrator();
		return new AddIntygComplementRequestCommand(arendeId, intygComplementRequestDateTime,bestallaradministrator);
	}

	private AddIntygApprovedCommand createAddIntygApprovedCommand(ArendeId arendeId) {
		DateTime intygApprovedDateTime = DateTime.now();
        Bestallaradministrator bestallaradministrator = createBestallaradministrator();
		return new AddIntygApprovedCommand(arendeId, intygApprovedDateTime, bestallaradministrator);
	}

	private ApproveEavropCommand createApproveEavropCommand(ArendeId arendeId) {
		DateTime eavropApprovedDateTime = DateTime.now();
	    Bestallaradministrator bestallaradministrator = createBestallaradministrator();
		String note = "Utredning accepterad.";
		return new ApproveEavropCommand(arendeId, eavropApprovedDateTime, bestallaradministrator, note);
	}
	
	private ApproveEavropCompensationCommand createApproveEavropCompensationCommand(ArendeId arendeId, boolean isApproved) {
		DateTime eavropCompensationApprovedDateTime = DateTime.now();
        Bestallarsamordnare bestallarsamordnare = createBestallarsamordnare();
		String note = (isApproved)?"Betalning godkänd.":"Betalning ej godkänd.";
		return new ApproveEavropCompensationCommand(arendeId, eavropCompensationApprovedDateTime, isApproved, bestallarsamordnare, note) ;
	}

}
