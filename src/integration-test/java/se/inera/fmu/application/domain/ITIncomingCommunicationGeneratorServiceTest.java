package se.inera.fmu.application.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.Application;
import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.IncomingCommunicationGeneratorService;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivareRepository;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.facade.FmuFacade;
import se.inera.fmu.infrastructure.security.FakeAuthenticationProvider;
import se.inera.fmu.interfaces.managing.rest.TestUtil;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TimeDTO;

/**
 * Integration Test class
 *
 * @see se.inera.fmu.application.impl.UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@Transactional
public class ITIncomingCommunicationGeneratorServiceTest {

	@Inject
	private IncomingCommunicationGeneratorService incomingCommunicationGeneratorService;
	@Inject
	private FmuFacade fmuFacade;
    
	@Inject
	private EavropRepository eavropRepository;
	@Inject 
	private VardgivareRepository vardgivareRepository;
	@Inject 
	private VardgivarenhetRepository vardgivarenhetRepository;
	@Inject
	private FmuListService fmuListService;

	@Inject
	private FakeAuthenticationProvider provider;
	@Inject
	private CurrentUserService currentUserService;
	
	private LandstingCode LANDSTING_CODE = new LandstingCode(1);
	private Long VARDGIVARENHET_ID;
	
    @Before
    @Transactional
	public void setUp(){
		Landsting landsting = getLandsting(LANDSTING_CODE);
		Vardgivare vardgivare = createVardgivare(new HsaId("SE160000000000-0000000AB"), "Personal Care AB");
		Vardgivarenhet enhet = createVardgivarenhet(new HsaId("SE160000000000-0000000AC"), "Roinekliniken", new Address("Barnhusgatan 12", "33443", "Stockholm", "Sverige"),"epost@epost","555-123123",vardgivare, landsting);
		VARDGIVARENHET_ID = enhet.getId(); 
		
		TestUtil.loginWithNoActiveRole(provider);
		currentUserService.getCurrentUser().setLandstingCode(LANDSTING_CODE.getCode());
		currentUserService.getCurrentUser().setVardenhetHsaId(enhet.getHsaId().toString());
    }
    
    @Test
    public void testEavropFlowUsingComGenerator(){
    	//Customer sends Eavrop request
    	ArendeId arendeId = incomingCommunicationGeneratorService.generateEavrop();    	
    	EavropId eavropId = getEavropId(arendeId);
    	assertNotNull(eavropId);
    	asserEavropStatus(eavropId, EavropStateType.UNASSIGNED);

		//The samordnare assigns the eavrop to the care giving unit
    	currentUserService.getCurrentUser().setActiveRole(Role.ROLE_SAMORDNARE);
    	fmuFacade.assignVardgivarenhetToEavrop(eavropId, VARDGIVARENHET_ID);
    	asserEavropStatus(eavropId, EavropStateType.ASSIGNED);
    	
    	//The care giver accepts the eavrop, and the customer gets notified that the eavrop is accepted and by whom.
    	currentUserService.getCurrentUser().setActiveRole(Role.ROLE_UTREDARE);
    	fmuFacade.acceptEavropAssignment(eavropId);
    	asserEavropStatus(eavropId, EavropStateType.ACCEPTED);
    	
    	//The customer sends communication that documents are in the mail, customer get notified that the eavrop vill start in three days.
    	incomingCommunicationGeneratorService.generateSentDocuments(arendeId);
    	asserEavropStatus(eavropId, EavropStateType.ACCEPTED);
    	
    	//The care giver books an appointment with the citizen 
    	BookingId bookingId = fmuFacade.addBooking(createBookingRequestDTO(eavropId));
    	assertNotNull(bookingId);
    	asserEavropStatus(eavropId, EavropStateType.ACCEPTED);
    	
    	//The citizen does not show up, the booking is canceled which in turn sest the eavrop on hold and a request for booking deviation respons is sent to the customer 
    	fmuFacade.modifyBooking(createBookingChangeRequestDTO(eavropId, bookingId));
    	asserEavropStatus(eavropId, EavropStateType.ON_HOLD);
    	
    	//The customer sends a booking deviation response, which states that the eavrop should be restarted 
    	incomingCommunicationGeneratorService.generateBookingDeviationResponse(arendeId, bookingId, BookingDeviationResponseType.RESTART);
    	asserEavropStatus(eavropId, EavropStateType.ONGOING);
    	
    	//The doctor has created the intyg in WebCert and it is sent to the customer, FMU gets notified.
    	incomingCommunicationGeneratorService.generateIntygSent(arendeId);
    	asserEavropStatus(eavropId, EavropStateType.SENT);
    	    	
    	//The customer needs additions made to the intyg and notifies WebCert, wivh in turn notifies FMU
    	incomingCommunicationGeneratorService.generateIntygComplementRequestInformation(arendeId);
    	asserEavropStatus(eavropId, EavropStateType.SENT);
    	
    	//The customer communicates that the Intyg is approved
    	incomingCommunicationGeneratorService.generateIntygApprovedInformation(arendeId);
    	asserEavropStatus(eavropId, EavropStateType.SENT);
    	
    	//The customer communicates that the Eavrop is approved
    	incomingCommunicationGeneratorService.generateEavropApproval(arendeId);
    	asserEavropStatus(eavropId, EavropStateType.APPROVED);
    	
    	//The customer communicates that the Compensation is approved
    	incomingCommunicationGeneratorService.generateEavropCompensationApproval(arendeId, Boolean.TRUE);
    	asserEavropStatus(eavropId, EavropStateType.CLOSED);
    }

    private void asserEavropStatus(EavropId eavropId, EavropStateType eavropStateType){
    	Eavrop eavrop = eavropRepository.findByEavropId(eavropId);
    	assertNotNull(eavrop);
    	assertEquals(eavropStateType, eavrop.getStatus());
    }
    
	private BookingRequestDTO createBookingRequestDTO(EavropId eavropId ){
    	BookingRequestDTO booking = new BookingRequestDTO();
		booking.setEavropId(eavropId.toString());
		booking.setBookingType(BookingType.EXAMINATION);
		booking.setBookingDate(new DateTime().getMillis());
		booking.setBookingStartTime(new TimeDTO().setHour(12).setMinute(30));
		booking.setBookingEndTime(new TimeDTO().setHour(13).setMinute(30));
		booking.setPersonName("Åsa Andersson");
		booking.setAdditionalService(true);
		booking.setPersonRole("Läkare");
		booking.setUseInterpreter(true);
    	return booking;
    }
    
    private BookingModificationRequestDTO createBookingChangeRequestDTO(
		EavropId eavropId, BookingId bookingId) {
    	BookingModificationRequestDTO bookingModification = new BookingModificationRequestDTO();
    	bookingModification.setEavropId(eavropId.toString());
    	bookingModification.setBookingId(bookingId.getId());
		bookingModification.setBookingStatus(BookingStatusType.CANCELLED_NOT_PRESENT);
		bookingModification.setComment("Citizen did not show up.");
		return bookingModification;
	}

    private EavropId getEavropId(ArendeId arendeId){
    	EavropId eavropId = null;
    	Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
    	if(eavrop!=null){
    		eavropId = eavrop.getEavropId();
    	}
    	return eavropId;
    }
    
    private Vardgivarenhet createVardgivarenhet(HsaId hsaId, String name, Address address, String email, String phone,Vardgivare vardgivare, Landsting landsting){
    	Vardgivarenhet vardgivarenhet = new Vardgivarenhet(vardgivare, hsaId, name, address, email, phone);
    	if(landsting != null ){
    		landsting.addVardgivarenhet(vardgivarenhet);
    		vardgivarenhet.addLandsting(landsting);
    	}
    	return vardgivarenhetRepository.saveAndFlush(vardgivarenhet);
    }
    
    private Vardgivare createVardgivare(HsaId hsaId, String name){
    	Vardgivare vardgivare = new Vardgivare(hsaId, name);
    	vardgivare = vardgivareRepository.saveAndFlush(vardgivare);
    	return vardgivare; 
    }
    
    private Landsting getLandsting(LandstingCode landstingCode){
    	return fmuListService.findLandstingByLandstingCode(landstingCode);
    }
}
