package se.inera.fmu.application.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

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
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.OnHoldEavropState;
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
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
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
import se.inera.fmu.domain.model.person.TolkPerson;
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

	private static EavropState[] COMPLETED_STATES = {new  ApprovedEavropState(), new ClosedEavropState() };

	private static EavropState[] ALL_STATES = {new  UnassignedEavropState(), new AssignedEavropState(), new  AcceptedEavropState(), new OnHoldEavropState(), new  ApprovedEavropState(), new ClosedEavropState() };
	
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
    	
    	eavrop = assignEavrop(eavrop, vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId));
    	eavrop = acceptEavrop(eavrop);
    	
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
    	
    	ArendeId arendeId = new ArendeId("TST");
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	
    	createEavrop(arendeId, landsting);
    	
    	Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	eavrop = assignEavrop(eavrop, vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId));
    	eavrop = acceptEavrop(eavrop);
    	eavrop = deviateEavrop(eavrop);
    	
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	
    	assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());
    	
    	assertNotNull(eavrop.getBookings());
        assertEquals(1, eavrop.getBookings().size());

        for (Booking booking : eavrop.getBookings()) {
        	assertNotNull(booking.getPerson());
        	assertEquals(Boolean.TRUE, booking.getBookingStatus().isDeviant());
        	assertNull(booking.getBookingDeviationResponse());
    	}

        eavrop = deviateRespondEavrop(eavrop);
    	
        eavrop = eavropRepository.findByArendeId(arendeId);
        
        assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());

        for (Booking booking : eavrop.getBookings()) {
        	assertNotNull(booking.getPerson());
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
    	Eavrop eavrop = createEavrop(new ArendeId("1"), landsting);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("1")));
    	assertEquals(EavropStateType.UNASSIGNED, eavropRepository.findByArendeId(new ArendeId("1")).getEavropState().getEavropStateType());
    	
    	//ASSIGNED
    	eavrop = createEavrop(new ArendeId("2"), landsting);
    	assignEavrop(eavrop, vardgivarenhet);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("2")));
    	assertEquals(EavropStateType.ASSIGNED, eavropRepository.findByArendeId(new ArendeId("2")).getEavropState().getEavropStateType());
    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("2")).getAssignments());
    	assertEquals(vardgivarenhet, eavropRepository.findByArendeId(new ArendeId("2")).getAssignments().iterator().next().getVardgivarenhet());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("3"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	acceptEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("3")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("3")).getEavropState().getEavropStateType());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("4"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	acceptEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("4")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("4")).getEavropState().getEavropStateType());

    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("5"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	deviateEavrop(eavrop);
//    	eavrop = createDeviatedEavrop(new ArendeId("5"), landsting);    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("5")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("5")).getEavropState().getEavropStateType());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("5")).getBookings());
    	assertEquals(1, eavropRepository.findByArendeId(new ArendeId("5")).getBookings().size());
    	
    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("6"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	deviateEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("6")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("6")).getEavropState().getEavropStateType());

    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("7"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("7")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("7")).getEavropState().getEavropStateType());

    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("8"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("8")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("8")).getEavropState().getEavropStateType());
    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("9"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("9")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("9")).getEavropState().getEavropStateType());

    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("10"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("10")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("10")).getEavropState().getEavropStateType());

    	
    	
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
    	
    	EavropState[] onHoldStates = {new OnHoldEavropState() };
    	eavrops = eavropRepository.findByLandstingAndEavropStateIn(landsting, Arrays.asList(onHoldStates));
    	assertNotNull(eavrops);
    	assertEquals(2, eavrops.size());
    	
    	for (Eavrop e : eavrops) {
        	assertNotNull(e.getBookings());
        	assertEquals(1, e.getBookings().size());

        	for (Booking booking : e.getBookings()) {
        		assertNotNull(booking.getPerson());
    		}
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
      	
    	Eavrop eavrop = createEavrop(new ArendeId("StartDate"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = startEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(new ArendeId("StartDate"));
    	
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
    public void testFindByLandstingAndIntygSignedDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
      	ArendeId arendeId = new ArendeId("SignedDate");
      	
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = startEavrop(eavrop);
    	eavrop = signIntygToday(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	assertEquals(EavropStateType.APPROVED, eavrop.getEavropState().getEavropStateType());
 
    	assertNotNull(eavrop.getIntygSignedDateTime());
    	
    	DateTime signDate = eavrop.getIntygSignedDateTime(); 
   
    	Page<Eavrop> eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, null, signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.minusDays(1), null, Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.minusDays(2), signDate.minusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.plusDays(1), signDate.plusDays(2), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByLandstingAndIntygSignedDateAndEavropStateIn(landsting, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }

    @Test
    public void testFindByVardgivarenhetAndCreateDateAndStates(){
    	ArendeId arendeId = new ArendeId("CreatetDate");
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	assertNotNull(vardgivarenhet);

    	DateTime today = new DateTime().withTimeAtStartOfDay(); 
   
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	
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
      	
    	Eavrop eavrop = createEavrop(new ArendeId("StartDate"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = startEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("StartDate")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("StartDate")).getEavropState().getEavropStateType());
    	
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
    public void testFindByVardgivarenhetAndIntygSignedDateAndStates(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);

      	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
      	assertNotNull(vardgivarenhet);
      	
      	ArendeId arendeId = new ArendeId("SignedDate");
      	
    	Eavrop eavrop = createEavrop(arendeId, landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = startEavrop(eavrop);
    	eavrop = signIntygToday(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = eavropRepository.findByArendeId(arendeId);
    	assertNotNull(eavrop);
    	
    	assertEquals(EavropStateType.APPROVED, eavrop.getEavropState().getEavropStateType());
 
    	assertNotNull(eavrop.getIntygSignedDateTime());
    	
    	DateTime signDate = eavrop.getIntygSignedDateTime(); 
   
    	Page<Eavrop> eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, null, signDate.plusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), null, Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(1, eavrops.getNumberOfElements());
    	
    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(2), signDate.minusDays(1), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());

    	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.plusDays(1), signDate.plusDays(2), Arrays.asList(COMPLETED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(NOT_ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    	
       	eavrops = eavropRepository.findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(vardgivarenhet, signDate.minusDays(1), signDate.plusDays(1), Arrays.asList(ACCEPTED_STATES), PAGEABLE);
    	assertNotNull(eavrops);
    	assertEquals(0, eavrops.getNumberOfElements());
    }

    
    @Test
    public void testGetEavropAsPage(){
    	
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	
    	//UNASSIGNED
    	Eavrop eavrop = createEavrop(new ArendeId("1"), landsting);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("1")));
    	assertEquals(EavropStateType.UNASSIGNED, eavropRepository.findByArendeId(new ArendeId("1")).getEavropState().getEavropStateType());
    	
    	//ASSIGNED
    	eavrop = createEavrop(new ArendeId("2"), landsting);
    	assignEavrop(eavrop, vardgivarenhet);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("2")));
    	assertEquals(EavropStateType.ASSIGNED, eavropRepository.findByArendeId(new ArendeId("2")).getEavropState().getEavropStateType());
    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("2")).getAssignments());
    	assertEquals(vardgivarenhet, eavropRepository.findByArendeId(new ArendeId("2")).getAssignments().iterator().next().getVardgivarenhet());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("3"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	acceptEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("3")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("3")).getEavropState().getEavropStateType());
    	
    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("4"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	acceptEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("4")));
    	assertEquals(EavropStateType.ACCEPTED, eavropRepository.findByArendeId(new ArendeId("4")).getEavropState().getEavropStateType());

    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("5"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	deviateEavrop(eavrop);
//    	eavrop = createDeviatedEavrop(new ArendeId("5"), landsting);    	
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("5")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("5")).getEavropState().getEavropStateType());
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("5")).getBookings());
    	assertEquals(1, eavropRepository.findByArendeId(new ArendeId("5")).getBookings().size());
    	
    	//ON_HOLD
    	eavrop = createEavrop(new ArendeId("6"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	deviateEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("6")));
    	assertEquals(EavropStateType.ON_HOLD, eavropRepository.findByArendeId(new ArendeId("6")).getEavropState().getEavropStateType());

    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("7"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("7")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("7")).getEavropState().getEavropStateType());

    	//ACCEPTED
    	eavrop = createEavrop(new ArendeId("8"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("8")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("8")).getEavropState().getEavropStateType());
    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("9"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = approveEavrop(eavrop);
    	approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("9")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("9")).getEavropState().getEavropStateType());

    	
    	//CLOSED
    	eavrop = createEavrop(new ArendeId("10"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	eavrop = approveEavrop(eavrop);
    	eavrop = approveEavropCompensation(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("10")));
    	assertEquals(EavropStateType.CLOSED, eavropRepository.findByArendeId(new ArendeId("10")).getEavropState().getEavropStateType());

    	
    	
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

    
//    private Landstingssamordnare createLandstingssamordnare(HsaId hsaId, Name name, HsaBefattning hsaBefattning, Landsting landsting){
//    	Landstingssamordnare landstingssamordnare = new Landstingssamordnare(hsaId, name, hsaBefattning, landsting);
//    	if(landsting != null ){
//    		landsting.addLandstingssamordnare(landstingssamordnare);
//    	}
//    	landstingssamordnareRepository.saveAndFlush(landstingssamordnare);
//    	
//    	return landstingssamordnare; 
//    }
    
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
        eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
        
        //accept
        eavrop.acceptEavropAssignment();
        
        //deviate
        Booking booking = createBooking();
        eavrop.addBooking(booking);
        eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, createNote());
        assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());   
    
        return eavropRepository.saveAndFlush(eavrop);
    }

    
    private Eavrop assignEavrop(Eavrop eavrop, Vardgivarenhet vardgivarenhet){
    	eavrop.assignEavropToVardgivarenhet(vardgivarenhet);
    	return eavropRepository.save(eavrop);
    }

    private Eavrop acceptEavrop(Eavrop eavrop){
    	eavrop.acceptEavropAssignment();
    	return eavropRepository.save(eavrop);
    }

    private Eavrop rejectEavrop(Eavrop eavrop){
    	eavrop.rejectEavropAssignment();
    	return eavropRepository.saveAndFlush(eavrop);
    }

    private Eavrop deviateEavrop(Eavrop eavrop){
    	
    	//Booking booking =  createBooking();
    	Person person = new TolkPerson("","");
    	
    	Booking booking = new Booking(BookingType.EXAMINATION, new DateTime(), new DateTime(), person, Boolean.FALSE);
    	assertNotNull(booking.getPerson());
    	
    	eavrop.addBooking(booking);
    	
    	for (Booking b : eavrop.getBookings()) {
    		assertNotNull(b.getPerson());
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
    
    private Eavrop signIntygToday(Eavrop eavrop){
    	
    	DateTime today = new DateTime();
    	eavrop.setCreatedDate(today.minusDays(6));

    	ReceivedDocument receivedDocument = new ReceivedDocument(today.minusDays(6), "Journal", new Bestallaradministrator("Ordny Ordnarsson", "Handläggare", "Försäkringskassan", "LFC Stockholm", "555-12345", "ordny@fk.se"), Boolean.TRUE);
    	eavrop.addReceivedDocument(receivedDocument);

    	IntygSignedInformation  intygSignedInformation = new IntygSignedInformation(today, new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus")); 
    	eavrop.addIntygSignedInformation(intygSignedInformation);
    	
    	return eavropRepository.save(eavrop);
    }

    
    private Eavrop deviateRespondEavrop(Eavrop eavrop){
    	
    	eavrop.addBookingDeviationResponse(eavrop.getBookings().iterator().next().getBookingId(),createBookingDeviationResponse());
    	
    	return eavropRepository.save(eavrop);
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
    	Invanare invanare = new Invanare(pnr, name, Gender.MALE, address, "dennis.ritchie@belllabs.com", null); 
    	
    	return invanareRepository.saveAndFlush(invanare);
    }

    private Bestallaradministrator createBestallaradministrator(){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator("Per Elofsson", "Handläggare", "Försäkringskassan", "LFC Nedre Dalarna", "010-1234567", "per.elofsson@fk.se");
    	
    	return bestallaradministrator;
    }   


    private Booking createBooking(){
    	
    	DateTime start = new DateTime();
    	DateTime end = start.plusHours(1);
    	Person person = new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus");
    	//Set<Person> persons = new HashSet<Person>();
    	//persons.add(person);
    	Booking booking = new Booking(BookingType.EXAMINATION, start,end, person, Boolean.FALSE);

    	return booking;
    }
    
    private BookingDeviation createBookingDeviation(){
    	
    	BookingDeviation deviation = new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, new Note(NoteType.BOOKING_DEVIATION, "No Show", new HoSPerson("Lasse Kongo", "Läkare", "Danderydssjukhus")));
    	return deviation;
    }

    
    private BookingDeviationResponse createBookingDeviationResponse(){
    	
    	Bestallaradministrator adm = new Bestallaradministrator("Törn Valdegård", "Driver", "Försäkringskassan", "STCC", "555-123456", "rattmuff@saab.se");
    	
    	BookingDeviationResponse deviation = new BookingDeviationResponse( BookingDeviationResponseType.RESTART, new DateTime(), adm, null );
    	
    	deviation.setDeviationResponseNote(new Note(NoteType.BOOKING_DEVIATION_RESPONSE, "Kör på", adm));
    	return deviation;
    }
    
    private Note createNote(){
    	
    	Note note = new Note(NoteType.EAVROP, "Test note", new HoSPerson("Test Testsson", "Testare", "AVD Testning"));
    	return note;
    }
    
    
}
