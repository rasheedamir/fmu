package se.inera.fmu.application.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.inera.fmu.Application;
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
import se.inera.fmu.domain.model.eavrop.OnHoldEavropStateTest;
import se.inera.fmu.domain.model.eavrop.UnassignedEavropState;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviation;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.hsa.HsaBefattning;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivareRepository;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.landsting.Landstingssamordnare;
import se.inera.fmu.domain.model.landsting.LandstingssamordnareRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.person.TolkPerson;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

import org.apache.activemq.filter.function.splitFunction;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

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

	@Inject
	private LandstingssamordnareRepository landstingssamordnareRepository;
	
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
    
    private HsaId landstingssamordnarId;
    
    private ArendeId arendeId;

    @Before
	public void setUp() throws Exception {
		this.landstingCode = new LandstingCode(99);
		Landsting landsting = createLandsting(this.landstingCode, "Stockholms Läns Landsting");

		this.landstingssamordnarId = new HsaId("SE160000000000-00000000A");
		Landstingssamordnare landstingssamordnare = createLandstingssamordnare(this.landstingssamordnarId, new Name("Sam", null, "Ordnarsson"), new HsaBefattning("S3", "Samordnare"), landsting);

		this.vardgivareId = new HsaId("SE160000000000-00000000B");
		Vardgivare vardgivare = createVardgivare(this.vardgivareId, "Personal Care AB");
		
		this.vardgivarenhetId = new HsaId("SE160000000000-00000000C");
		Vardgivarenhet vardgivarenhet = createVardgivarenhet(this.vardgivarenhetId, "Roinekliniken", new Address("Barnhusgatan 12", "33443", "Stockholm", "Sverige"),vardgivare, landsting);
		
		this.arendeId = new ArendeId("140212042744");
		Eavrop eavrop = createEavrop(this.arendeId, landsting);
	}
    
    @Test
    public void testLandsting(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	System.out.print(landsting);
    }
    
    @Test
    public void testLandstingsamordnare(){
    	Landstingssamordnare landstingssamordnare = landstingssamordnareRepository.findByHsaId(this.landstingssamordnarId);
    	assertNotNull(landstingssamordnare);
    	System.out.print(landstingssamordnare);
    }
    
    @Test
    public void testVardgivare(){
    	Vardgivare vardgivare = vardgivareRepository.findByHsaId(this.vardgivareId);
    	assertNotNull(vardgivare);
    	System.out.print(vardgivare);
    }

    @Test
    public void testVardgivarenhet(){
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
    	assertNotNull(vardgivarenhet);
    	System.out.print(vardgivarenhet);
    }
    
    @Test
    public void testGetLandstingsamordnareFromLandsting(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	Set<Landstingssamordnare> samordnare = landsting.getLandstingssamordnare();
    	assertNotNull(samordnare);
    	assertEquals(1, samordnare.size());
    	Landstingssamordnare landstingssamordnare = samordnare.iterator().next();
    	assertEquals(landstingssamordnarId, landstingssamordnare.getHsaId());
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
    	
    	Vardgivarenhet vardgivarenhet = vardgivarenhetRepository.findByHsaId(this.vardgivarenhetId);
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
        	assertNotNull(booking.getPersons());
        	assertEquals(1, booking.getPersons().size());
        	assertNotNull(booking.getBookingDeviation());
        	assertNull(booking.getBookingDeviation().getBookingDeviationResponse());
    	}

        eavrop = deviateRespondEavrop(eavrop);
    	
        eavrop = eavropRepository.findByArendeId(arendeId);
        
        assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());

        for (Booking booking : eavrop.getBookings()) {
        	assertNotNull(booking.getPersons());
        	assertEquals(1, booking.getPersons().size());
        	assertNotNull(booking.getBookingDeviation());
        	assertNotNull(booking.getBookingDeviation().getBookingDeviationResponse());
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

    	//APPROVED
    	eavrop = createEavrop(new ArendeId("7"), landsting);
    	eavrop = assignEavrop(eavrop, vardgivarenhet);
    	eavrop = acceptEavrop(eavrop);
    	approveEavrop(eavrop);
    	assertNotNull(eavropRepository.findByArendeId(new ArendeId("7")));
    	assertEquals(EavropStateType.APPROVED, eavropRepository.findByArendeId(new ArendeId("7")).getEavropState().getEavropStateType());

    	//APPROVED
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
        		assertNotNull(booking.getPersons());
        		assertEquals(1, booking.getPersons().size());
    		}
		}
    }
    
    @Test
    public void testGetVardgivarenhetFromLandsting(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	Set<Vardgivarenhet> vardgivarenheter = landsting.getVardgivarenheter();
    	assertNotNull(vardgivarenheter);
    	assertEquals(1, vardgivarenheter.size());
    	Vardgivarenhet vardgivarenhet = vardgivarenheter.iterator().next();
    	assertEquals(vardgivarenhetId, vardgivarenhet.getHsaId());
    	Vardgivare vardgivare = vardgivarenhet.getVardgivare();
    	assertNotNull(vardgivare);
    }


    private Landstingssamordnare createLandstingssamordnare(HsaId hsaId, Name name, HsaBefattning hsaBefattning, Landsting landsting){
    	Landstingssamordnare landstingssamordnare = new Landstingssamordnare(hsaId, name, hsaBefattning, landsting);
    	if(landsting != null ){
    		landsting.addLandstingssamordnare(landstingssamordnare);
    	}
    	landstingssamordnareRepository.saveAndFlush(landstingssamordnare);
    	
    	return landstingssamordnare; 
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
        eavrop.cancelBooking(booking.getBookingId(), createBookingDeviation());
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
    	
    	Booking booking = new Booking(BookingType.EXAMINATION,new LocalDateTime(), new LocalDateTime(), person);
    	assertNotNull(booking.getPersons());
    	
    	eavrop.addBooking(booking);
    	
    	
    	for (Booking b : eavrop.getBookings()) {
    		assertNotNull(b.getPersons());
		}
    	
    	eavrop.cancelBooking(booking.getBookingId(), createBookingDeviation());
    	
    	return eavropRepository.save(eavrop);
    }

    
    private Eavrop deviateRespondEavrop(Eavrop eavrop){
    	
    	eavrop.addBookingDeviationResponse(eavrop.getBookings().iterator().next().getBookingId(),createBookingDeviationResponse());
    	
    	return eavropRepository.save(eavrop);
    }

    
    private Eavrop approveEavrop(Eavrop eavrop){
    	eavrop.approveEavrop(new EavropApproval(new LocalDateTime(), createBestallaradministrator()));
    	return eavropRepository.saveAndFlush(eavrop);
    }

    private Eavrop approveEavropCompensation(Eavrop eavrop){
    	eavrop.approveEavropCompensation(new EavropCompensationApproval(new LocalDateTime(), new Bestallaradministrator("1","2","3","4","5@eve.com")));
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
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator("Per Elofsson", "Handläggare", "LFC Nedre Dalarna", "010-1234567", "per.elofsson@fk.se");
    	
    	return bestallaradministrator;
    }   


    private Booking createBooking(){
    	
    	LocalDateTime start = new LocalDateTime();
    	LocalDateTime end = start.plusHours(1);
    	Person person = new HoSPerson("Dr Hudson", "Surgeon", "Danderyds sjukhus");
    	//Set<Person> persons = new HashSet<Person>();
    	//persons.add(person);
    	Booking booking = new Booking(BookingType.EXAMINATION, start,end, person);

    	return booking;
    }
    
    private BookingDeviation createBookingDeviation(){
    	
    	BookingDeviation deviation = new BookingDeviation(BookingDeviationType.INVANARE_CANCELLED_LT_48, new Note("No Show", new HoSPerson("Lasse Kongo", "Läkare", "Danderydssjukhus")));
    	return deviation;
    }

    
    private BookingDeviationResponse createBookingDeviationResponse(){
    	
    	Bestallaradministrator adm = new Bestallaradministrator("Törn Valdegård", "Driver", "STCC", "555-123456", "rattmuff@saab.se");
    	
    	BookingDeviationResponse deviation = new BookingDeviationResponse( BookingDeviationResponseType.RESTART, new LocalDateTime(), adm );
    	
    	deviation.setDeviationResponseNote(new Note("Kör på", adm));
    	return deviation;
    }
    
    private Note createNote(){
    	
    	Note note = new Note("Test note", new HoSPerson("Test Testsson", "Testare", "AVD Testning"));
    	return note;
    }
    
    
}
