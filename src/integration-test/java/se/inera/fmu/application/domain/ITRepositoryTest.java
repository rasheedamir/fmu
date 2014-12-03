package se.inera.fmu.application.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.Application;
import se.inera.fmu.application.util.BusinessDaysUtil;
import se.inera.fmu.domain.model.eavrop.AcceptedEavropState;
import se.inera.fmu.domain.model.eavrop.ApprovedEavropState;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.AssignedEavropState;
import se.inera.fmu.domain.model.eavrop.ClosedEavropState;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropApproval;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApproval;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.OnHoldEavropState;
import se.inera.fmu.domain.model.eavrop.SentEavropState;
import se.inera.fmu.domain.model.eavrop.UnassignedEavropState;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivareRepository;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Integration Test class for the UserResource REST controller.
 *
 * @see se.inera.fmu.application.impl.UserService
 */
@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@Transactional
public class ITRepositoryTest {

	@Inject
	private LandstingRepository landstingRepository;

//	@Inject
//	private LandstingssamordnareRepository landstingssamordnareRepository;
	
	@Inject
	private VardgivareRepository vardgivareRepository;

	@Inject
	private VardgivarenhetRepository vardgivarenhetRepository;

	@Inject
	private InvanareRepository invanareRepository;

	@Inject
	private EavropRepository eavropRepository;

	private LandstingCode landstingCode;
    
    private HsaId vardgivareId;
    
    private HsaId vardgivarenhetId;
    
//    private HsaId landstingssamordnarId;
    
    private ArendeId arendeId;
    
	private static EavropState[] NOT_ACCEPTED_STATES = {new  UnassignedEavropState(), new AssignedEavropState() };

	private static EavropState[] ACCEPTED_STATES = {new  AcceptedEavropState(), new OnHoldEavropState() };

	private static EavropState[] COMPLETED_STATES = {new SentEavropState(), new  ApprovedEavropState(), new ClosedEavropState() };

	private static EavropState[] ALL_STATES = {new  UnassignedEavropState(), new AssignedEavropState(), new  AcceptedEavropState(), new OnHoldEavropState(), new SentEavropState(), new  ApprovedEavropState(), new ClosedEavropState() };
	
	private static final  PageRequest PAGEABLE = new PageRequest( 0, 100, Direction.ASC, "arendeId");

    @Before
	public void setUp() throws Exception {
		this.landstingCode = new LandstingCode(99);
		Landsting landsting = createLandsting(this.landstingCode, "Stockholms Läns Landsting");

//		this.landstingssamordnarId = new HsaId("SE160000000000-00000000A");
//		Landstingssamordnare landstingssamordnare = createLandstingssamordnare(this.landstingssamordnarId, new Name("Sam", null, "Ordnarsson"), new HsaBefattning("S3", "Samordnare"), landsting);

		this.vardgivareId = new HsaId("SE160000000000-00000000B");
		Vardgivare vardgivare = createVardgivare(this.vardgivareId, "Personal Care AB");
		
		this.vardgivarenhetId = new HsaId("SE160000000000-00000000C");
		Vardgivarenhet vardgivarenhet = createVardgivarenhet(this.vardgivarenhetId, "Roinekliniken", new Address("Barnhusgatan 12", "33443", "Stockholm", "Sverige"),vardgivare, landsting);
		
		this.arendeId = new ArendeId("140212042744");
		Eavrop eavrop = createEavrop(this.arendeId, landsting);
	}
    

    @Test
    public void testGetEavrop(){
    	
    	Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	assertEquals(arendeId, eavrop.getArendeId());
    }

    @Test
    public void testGetEavropFromLandsting(){
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	List<Eavrop> eavrops = eavropRepository.findAllByLandsting(landsting);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.size());
    }
    
    
    @Test
    public void testAddNote(){
    	
    	Eavrop eavrop = eavropRepository.findByArendeId(this.arendeId);
    	assertNotNull(eavrop);
    	
    	eavrop = assignEavrop(eavrop, vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId),createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	
    	Note note = createNote();
    	eavrop.addNote(note);
    	eavropRepository.save(eavrop);
    	   	
    	eavrop = eavropRepository.findByArendeId(this.arendeId);
    	
    	assertEquals(note, eavrop.getNotes().iterator().next());
    	
    	note = createNote();
    	eavrop.addNote(note);
    	eavropRepository.save(eavrop);

    	eavrop = eavropRepository.findByArendeId(this.arendeId);
    	assertEquals(2, eavrop.getNotes().size());
    	
    }

    @Test
    public void testOnhold(){
    	
    	ArendeId arendeId = new ArendeId("010000000021");
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	
    	createEavrop(arendeId, landsting);
    	
    	Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	eavrop = assignEavrop(eavrop, vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId),createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	eavrop = deviateEavrop(eavrop);
    	
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	
    	assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());
    	
    	assertNotNull(eavrop.getBookings());
        assertEquals(1, eavrop.getBookings().size());

        for (Booking booking : eavrop.getBookings()) {
        	assertNotNull(booking.getBookingResource());
        	assertEquals(Boolean.TRUE, booking.getBookingStatus().isDeviant());
        	assertNull(booking.getBookingDeviationResponse());
    	}

        eavrop = deviateRespondEavrop(eavrop);
    	
        eavrop = eavropRepository.findByArendeId(arendeId);
        
        assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());

        for (Booking booking : eavrop.getBookings()) {
        	assertNotNull(booking.getBookingResource());
        	assertEquals(Boolean.TRUE, booking.getBookingStatus().isDeviant());
        	assertNotNull(booking.getBookingDeviationResponse());
    	}
    }


    @Test
    public void testGetEavropFromLandstingWithStatus(){
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	
    	//UNASSIGNED
    	Eavrop eavrop = createEavrop(new ArendeId("010000000011"), landsting);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000011")));
    	assertEquals(EavropStateType.UNASSIGNED, eavropRepository.findByArendeId(new ArendeId("010000000011")).getEavropState().getEavropStateType());
    	
    	//ASSIGNED
    	eavrop = createEavrop(new ArendeId("010000000012"), landsting);
    	assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000012")));
    	assertEquals(EavropStateType.ASSIGNED, eavropRepository.findByArendeId(new ArendeId("010000000012")).getEavropState().getEavropStateType());
    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000012")).getAssignments());
    	assertEquals(vardgivarenhet, eavropRepository.findByArendeId(new ArendeId("010000000012")).getAssignments().iterator().next().getVardgivarenhet());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("010000000013"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	acceptEavrop(eavrop,createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000013")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("010000000013")).getEavropState().getEavropStateType());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("010000000014"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	acceptEavrop(eavrop,createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000014")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("010000000014")).getEavropState().getEavropStateType());

    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("010000000015"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	deviateEavrop(eavrop);
//    	eavrop = createDeviatedEavrop(new ArendeId("010000000015"), landsting);    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000015")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("010000000015")).getEavropState().getEavropStateType());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000015")).getBookings());
    	assertEquals(1, eavropRepository.findByArendeId(new ArendeId("010000000015")).getBookings().size());
    	
    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("010000000016"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	deviateEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000016")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("010000000016")).getEavropState().getEavropStateType());

    	//SENT
    	eavrop = createEavrop(new ArendeId("010000000017"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	sendIntyg(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000017")));
    	assertEquals(EavropStateType.SENT, eavropRepository.findByArendeId(new ArendeId("010000000017")).getEavropState().getEavropStateType());

    	//APPROVED
    	eavrop = createEavrop(new ArendeId("010000000018"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000018")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("010000000018")).getEavropState().getEavropStateType());
    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("010000000019"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000019")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("010000000019")).getEavropState().getEavropStateType());

    	//CLOSED
    	eavrop = createEavrop(new ArendeId("010000000020"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000020")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("010000000020")).getEavropState().getEavropStateType());

    	
    	List<Eavrop> eavrops = eavropRepository.findAllByLandsting(landsting);
    	assertNotNull(eavrops);
    	assertEquals(11, eavrops.size());

    	EavropState[] notAcceptedStates = {new UnassignedEavropState(),new AssignedEavropState() };
    	
    	eavrops = eavropRepository.findByLandstingAndEavropStateIn(landsting, Arrays.asList(notAcceptedStates));
    	assertNotNull(eavrops);
    	assertEquals(3, eavrops.size());
    	EavropState[] closedStates = {new ClosedEavropState() };
    	eavrops = eavropRepository.findByLandstingAndEavropStateIn(landsting, Arrays.asList(closedStates));
    	assertNotNull(eavrops);
    	assertEquals(2, eavrops.size());

    	EavropState[] sentStates = {new SentEavropState() };
    	eavrops = eavropRepository.findByLandstingAndEavropStateIn(landsting, Arrays.asList(sentStates));
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.size());
    	
    	EavropState[] onHoldStates = {new OnHoldEavropState() };
    	eavrops = eavropRepository.findByLandstingAndEavropStateIn(landsting, Arrays.asList(onHoldStates));
    	assertNotNull(eavrops);
    	assertEquals(2, eavrops.size());
    	
    	for (Eavrop e : eavrops) {
        	assertNotNull(e.getBookings());
        	assertEquals(1, e.getBookings().size());

        	for (Booking booking : e.getBookings()) {
        		assertNotNull(booking.getBookingResource());
    		}
		}
    }

    
    @Test
    public void testDocumentType(){
    	
    	Eavrop eavrop = eavropRepository.findByEavropId(new EavropId("16"));
    	assertNotNull(eavrop);
    	
    	Set<ReceivedDocument> recDocs = eavrop.getReceivedDocuments();

    	assertNotNull(recDocs);
    	
    	for (ReceivedDocument receivedDocument : recDocs) {
			System.out.println(receivedDocument.toString());
		}
    	
    	Set<RequestedDocument> reqDocs = eavrop.getRequestedDocuments();
      	assertNotNull(reqDocs);
    	for (RequestedDocument requestedDocument : reqDocs) {
    		System.out.println(requestedDocument.toString());
		}
    }

    @Test
    public void testFindByLandstingAndCreateDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	DateTime today = new DateTime().withTimeAtStartOfDay(); 
   
    	Page<Eavrop> eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.minusDays(1), today.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES),PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, null, today.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.minusDays(1), null, Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.minusDays(2), today.minusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.plusDays(1), today.plusDays(2), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.minusDays(1), today.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, today.minusDays(1), today.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }
    
    @Test
    public void testFindByLandstingAndStartDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
    	Eavrop eavrop = createEavrop(new ArendeId("010000000004"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet,createHoSPerson());
    	eavrop = acceptEavrop(eavrop,createHoSPerson());
    	eavrop = startEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(new ArendeId("010000000004"));
    	
    	assertNotNull(eavrop);
    	assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());

    	assertNotNull(eavrop.getStartDate());
    	LocalDate startDate = eavrop.getStartDate(); 
    	
    	assertTrue(startDate.minusDays(1).isBefore(eavrop.getStartDate()));
    	assertTrue(startDate.plusDays(1).isAfter(eavrop.getStartDate()));
    	
    	Page<Eavrop> eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, null, startDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.minusDays(1), null, Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.minusDays(2), startDate.minusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.plusDays(1), startDate.plusDays(2), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }


    @Test
    public void testFindByLandstingAndIntygSentDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
      	ArendeId arendeId = new ArendeId("010000000003");
      	
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = startEavrop(eavrop);
    	eavrop = sendIntygToday(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	assertEquals(EavropStateType.APPROVED, eavrop.getEavropState().getEavropStateType());
 
    	assertNotNull(eavrop.getIntygSentDateTime());
    	
    	DateTime signDate = eavrop.getIntygSentDateTime(); 
   
    	Page<Eavrop> eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, null, signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.minusDays(1), null, Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.minusDays(2), signDate.minusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.plusDays(1), signDate.plusDays(2), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }

    @Test
    public void testFindByVardgivarenhetAndCreateDateAndStates(){
    	ArendeId arendeId = new ArendeId("010000000005");
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	assertNotNull(vardgivarenhet);

    	DateTime today = new DateTime().withTimeAtStartOfDay(); 
   
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	
    	assertNotNull(eavrop);
    	assertNotNull(eavrop.getCreatedDate());

    	assertEquals(EavropStateType.ASSIGNED, eavrop.getEavropState().getEavropStateType());
    	
    	Page<Eavrop> eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.minusDays(1), today.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, null, today.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.minusDays(1), null, Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.minusDays(2), today.minusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.plusDays(1), today.plusDays(2), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.minusDays(1), today.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, today.minusDays(1), today.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }
    
    @Test
    public void testFindByVardgivarenhetAndStartDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
    	Eavrop eavrop = createEavrop(new ArendeId("010000000007"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = startEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000007")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("010000000007")).getEavropState().getEavropStateType());
    	
    	LocalDate startDate = eavrop.getStartDate();
    	assertNotNull(startDate);
    	
    	Page<Eavrop> eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, null, startDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.minusDays(1), null, Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.minusDays(2), startDate.minusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.plusDays(1), startDate.plusDays(2), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, startDate.minusDays(1), startDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

 
    }


    @Test
    public void testFindByVardgivarenhetAndIntygSendDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
      	ArendeId arendeId = new ArendeId("010000000006");
      	
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = startEavrop(eavrop);
    	eavrop = sendIntygToday(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	assertEquals(EavropStateType.APPROVED, eavrop.getEavropState().getEavropStateType());
 
    	assertNotNull(eavrop.getIntygSentDateTime());
    	
    	DateTime signDate = eavrop.getIntygSentDateTime(); 
   
    	Page<Eavrop> eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, null, signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), null, Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(2), signDate.minusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.plusDays(1), signDate.plusDays(2), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }

    
    @Test
    public void testGetEavropAsPage(){
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	
    	//UNASSIGNED
    	Eavrop eavrop = createEavrop(new ArendeId("010000000011"), landsting);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000011")));
    	assertEquals(EavropStateType.UNASSIGNED, eavropRepository.findByArendeId(new ArendeId("010000000011")).getEavropState().getEavropStateType());
    	
    	//ASSIGNED
    	eavrop = createEavrop(new ArendeId("010000000012"), landsting);
    	assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000012")));
    	assertEquals(EavropStateType.ASSIGNED, eavropRepository.findByArendeId(new ArendeId("010000000012")).getEavropState().getEavropStateType());
    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000012")).getAssignments());
    	assertEquals(vardgivarenhet, eavropRepository.findByArendeId(new ArendeId("010000000012")).getAssignments().iterator().next().getVardgivarenhet());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("010000000013"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	acceptEavrop(eavrop, createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000013")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("010000000013")).getEavropState().getEavropStateType());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("010000000014"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	acceptEavrop(eavrop, createHoSPerson());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000014")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("010000000014")).getEavropState().getEavropStateType());

    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("010000000015"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	deviateEavrop(eavrop);
//    	eavrop = createDeviatedEavrop(new ArendeId("010000000015"), landsting);    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000015")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("010000000015")).getEavropState().getEavropStateType());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000015")).getBookings());
    	assertEquals(1, eavropRepository.findByArendeId(new ArendeId("010000000015")).getBookings().size());
    	
    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("010000000016"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	deviateEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000016")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("010000000016")).getEavropState().getEavropStateType());

    	//SENT
    	eavrop = createEavrop(new ArendeId("010000000017"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	sendIntyg(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000017")));
    	assertEquals(EavropStateType.SENT, eavropRepository.findByArendeId(new ArendeId("010000000017")).getEavropState().getEavropStateType());

    	//APPROVED
    	eavrop = createEavrop(new ArendeId("010000000018"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000018")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("010000000018")).getEavropState().getEavropStateType());
    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("010000000019"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000019")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("010000000019")).getEavropState().getEavropStateType());

    	//CLOSED
    	eavrop = createEavrop(new ArendeId("010000000020"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet, createHoSPerson());
    	eavrop = acceptEavrop(eavrop, createHoSPerson());
    	eavrop = sendIntyg(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("010000000020")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("010000000020")).getEavropState().getEavropStateType());
    	
    	List<Eavrop> eavrops = eavropRepository.findAllByLandsting(landsting);
    	assertNotNull(eavrops);
    	assertEquals(11, eavrops.size());
    	
    	
    	Pageable pageable = new PageRequest(0, 5, new Sort(new Order(Direction.DESC, "arendeId")));
    	
    	Page<Eavrop> eavropPage = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, null, null, Arrays.asList(ALL_STATES), pageable);
    	
    	assertEquals(5, eavropPage.getNumberOfElements());
    	
    	pageable = new PageRequest(1, 5, new Sort(new Order(Direction.DESC, "arendeId")));
      	
    	eavropPage = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, null, null, Arrays.asList(ALL_STATES), pageable);
    	
    	assertEquals(5, eavropPage.getNumberOfElements());
    	
    	pageable = new PageRequest(2, 5, new Sort(new Order(Direction.DESC, "arendeId")));
      	
    	eavropPage = eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, null, null, Arrays.asList(ALL_STATES), pageable);
    	
    	assertEquals(1, eavropPage.getNumberOfElements());
    }
    
    private Vardgivarenhet createVardgivarenhet(HsaId hsaId, String name, Address address, Vardgivare vardgivare, Landsting landsting){
    	Vardgivarenhet vardgivarenhet = new Vardgivarenhet(vardgivare, hsaId, name, address );
    	if(landsting != null ){
    		landsting.addVardgivarenhet(vardgivarenhet);
    		vardgivarenhet.addLandsting(landsting); //TODO: necessary relation ?
    	}
    	vardgivarenhetRepository.saveAndFlush(vardgivarenhet);
    	
    	return vardgivarenhet; 
    }
    
    private Vardgivare createVardgivare(HsaId hsaId, String name){
    	Vardgivare vardgivare = new Vardgivare(hsaId, name);
    	vardgivareRepository.saveAndFlush(vardgivare);
    	return vardgivare; 
    }
    
    private Landsting createLandsting(LandstingCode landstingCode, String name){
    	Landsting landsting = new Landsting(landstingCode, name);
  
    	return landstingRepository.saveAndFlush(landsting);
    }
    
    private Eavrop createEavrop(ArendeId arendeId, Landsting landsting){
    	Invanare invanare = createInvanare(); 
    	Bestallaradministrator bestallaradministrator = createBestallaradministrator();
    	
        Eavrop eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(UtredningType.TMU) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
   
        return eavropRepository.save(eavrop);
    }

    
    private Eavrop createDeviatedEavrop(ArendeId arendeId, Landsting landsting){
    	Invanare invanare = createInvanare(); 
    	Bestallaradministrator bestallaradministrator = createBestallaradministrator();
    	
        Eavrop eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(UtredningType.TMU) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withEavropProperties(new EavropProperties(3,5,25,10))
		.build();
        
        //assign
        Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
        assertNotNull(vardgivarenhet);
        eavrop.assignEavropToVardgivarenhet(vardgivarenhet, createHoSPerson());
        
        //accept
        eavrop.acceptEavropAssignment(createHoSPerson());
        
        //deviate
        Booking booking = createBooking();
        eavrop.addBooking(booking);
        eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, createNote());
        assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());   
    
        return eavropRepository.saveAndFlush(eavrop);
    }

    
    private Eavrop assignEavrop(Eavrop eavrop, Vardgivarenhet vardgivarenhet, HoSPerson assigningPerson){
    	eavrop.assignEavropToVardgivarenhet(vardgivarenhet, assigningPerson);
    	return eavropRepository.save(eavrop);
    }

    private Eavrop acceptEavrop(Eavrop eavrop, HoSPerson acceptingPerson){
    	eavrop.acceptEavropAssignment(acceptingPerson);
    	return eavropRepository.save(eavrop);
    }

    private Eavrop rejectEavrop(Eavrop eavrop, HoSPerson rejectingPerson){
    	eavrop.rejectEavropAssignment(rejectingPerson, null);
    	return eavropRepository.saveAndFlush(eavrop);
    }

    private Eavrop deviateEavrop(Eavrop eavrop){
    	
    	//Booking booking =  createBooking();
    	Person person = new HoSPerson(null,"","","","");
    	
    	Booking booking = new Booking(BookingType.EXAMINATION, new DateTime(), new DateTime().plusHours(1), Boolean.FALSE, person.getName(), person.getRole(), Boolean.FALSE);
    	assertNotNull(booking.getBookingResource());
    	
    	eavrop.addBooking(booking);
    	
    	for (Booking b : eavrop.getBookings()) {
    		assertNotNull(b.getBookingResource());
		}
    	
    	eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_LT_48_H, createNote());
    	
    	return eavropRepository.save(eavrop);
    }

    private Eavrop startEavrop(Eavrop eavrop){
    	
    	
    	DateTime monday = new DateTime().withDayOfWeek(DateTimeConstants.MONDAY);

    	eavrop.setCreatedDate(monday.minusDays(3));
    	
    	//TODO: GO through documenst sent property vs add received document
    	//eavrop.setDateTimeDocumentsSentFromBestallare(date);
       	ReceivedDocument receivedDocument = new ReceivedDocument(monday, "Journal", new Bestallaradministrator("Ordny Ordnarsson", "Handläggare", "Försäkringskassan", "LFC Stockholm", "555-12345", "ordny@fk.se"), Boolean.TRUE);
    	eavrop.addReceivedDocument(receivedDocument);
   	
    	
    	return eavropRepository.save(eavrop);
    }

    
    private DateTime getTodayWithOffset(int offset){
    	LocalDate today = new LocalDate();
    	LocalDate date = new LocalDate();
    	int idx = 0;
    	while(BusinessDaysUtil.calculateBusinessDayDate(date, offset).isAfter(today)){
    		date = date.minusDays(1);
    		idx++;
    	}
    
    	return new DateTime().minusDays(idx);
    }
    
    private Eavrop sendIntygToday(Eavrop eavrop){
    	
    	DateTime today = new DateTime();
    	eavrop.setCreatedDate(today.minusDays(6));

    	ReceivedDocument receivedDocument = new ReceivedDocument(today.minusDays(6), "Journal", new Bestallaradministrator("Ordny Ordnarsson", "Handläggare", "Försäkringskassan", "LFC Stockholm", "555-12345", "ordny@fk.se"), Boolean.TRUE);
    	eavrop.addReceivedDocument(receivedDocument);

    	IntygSentInformation  intygSentInformation = new IntygSentInformation(today, createHoSPerson()); 
    	eavrop.addIntygSentInformation(intygSentInformation);
    	
    	return eavropRepository.save(eavrop);
    }

    private HoSPerson createHoSPerson(){
    	return new HoSPerson(new HsaId("SE160000000000-HAHAHHSBB"), "Dr Hudson", "Surgeon", "Danderyds sjukhus", "AVD fmu");
    }
    
    private Eavrop deviateRespondEavrop(Eavrop eavrop){
    	eavrop.addBookingDeviationResponse(eavrop.getBookings().iterator().next().getBookingId(),createBookingDeviationResponse());
    	return eavropRepository.save(eavrop);
    }

    private Eavrop sendIntyg(Eavrop eavrop){
    	IntygSentInformation  intygSentInformation = new IntygSentInformation(new DateTime(), new HoSPerson(new HsaId("SE160000000000-HAHAHHSBB"), "Dr Hudson", "Surgeon", "Danderyds sjukhus", "AVD fmu")); 
    	eavrop.addIntygSentInformation(intygSentInformation);
    	return eavropRepository.saveAndFlush(eavrop);
    }

    private Eavrop approveEavrop(Eavrop eavrop){
    	eavrop.approveEavrop(new EavropApproval(new DateTime(), createBestallaradministrator()));
    	return eavropRepository.saveAndFlush(eavrop);
    }

    private Eavrop approveEavropCompensation(Eavrop eavrop){
    	eavrop.approveEavropCompensation(new EavropCompensationApproval(Boolean.TRUE, new DateTime(), new Bestallaradministrator("1","2","3.0","3","4","5@eve.com")));
    	return eavropRepository.saveAndFlush(eavrop);
    }
    
    private Invanare createInvanare(){
    	PersonalNumber pnr = new PersonalNumber("700101-0101");
    	Name name = new Name("Dennis", null, "Ritchie");
    	Address address  = new Address("Bell Labs", "07974", "California", "USA");
    	Invanare invanare = new Invanare(pnr, name, Gender.MALE, address, "555-1233445", "dennis.ritchie@belllabs.com", null); 
    	
    	return invanareRepository.saveAndFlush(invanare);
    }

    private Bestallaradministrator createBestallaradministrator(){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator("Per Elofsson", "Handläggare", "Försäkringskassan", "LFC Nedre Dalarna", "010-1234567", "per.elofsson@fk.se");
    	
    	return bestallaradministrator;
    }   


    private Booking createBooking(){
    	
    	DateTime start = new DateTime();
    	DateTime end = start.plusHours(1);
    	Person person = new HoSPerson(new HsaId("SE160000000000-HAHAHHSBB"), "Dr Hudson", "Surgeon", "Danderyds sjukhus", "AVD fmu");
    	Booking booking = new Booking(BookingType.EXAMINATION, start,end, Boolean.FALSE, person.getName(), person.getRole(), Boolean.FALSE);

    	return booking;
    }
    
    private BookingDeviation createBookingDeviation(){
    	
    	BookingDeviation deviation = new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, new Note(NoteType.BOOKING_DEVIATION, "No Show", new HoSPerson(new HsaId("SE160000000000-HAHAHHSLC"), "Lasse Kongo", "Läkare", "Danderydssjukhus", "AVD fmu")));
    	return deviation;
    }

    
    private BookingDeviationResponse createBookingDeviationResponse(){
    	
    	Bestallaradministrator adm = new Bestallaradministrator("Törn Valdegård", "Driver", "Försäkringskassan", "STCC", "555-123456", "rattmuff@saab.se");
    	
    	BookingDeviationResponse deviation = new BookingDeviationResponse( BookingDeviationResponseType.RESTART, new DateTime(), adm, null );
    	
    	deviation.setDeviationResponseNote(new Note(NoteType.BOOKING_DEVIATION_RESPONSE, "Kör på", adm));
    	return deviation;
    }
    
    private Note createNote(){
    	
    	Note note = new Note(NoteType.EAVROP, "Test note", new HoSPerson(new HsaId("SE160000000000-HAHAHHTST"),"Test Testsson", "Testare", "Test AB", "AVD Testning"));
    	return note;
    }
    
    
}
