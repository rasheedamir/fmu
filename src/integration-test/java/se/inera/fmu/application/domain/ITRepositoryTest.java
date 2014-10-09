package se.inera.fmu.application.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.inera.fmu.Application;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
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
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.party.Bestallaradministrator;
import se.inera.fmu.domain.party.HoSParty;
import se.inera.fmu.domain.party.Party;

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
  
    	landstingRepository.saveAndFlush(landsting);
    	return landsting;
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

    	eavrop.addBooking(createBooking());
    	
    	eavropRepository.saveAndFlush(eavrop);
    	return eavrop;
    }
    
    private Invanare createInvanare(){
    	PersonalNumber pnr = new PersonalNumber("700101-0101");
    	Name name = new Name("Dennis", null, "Ritchie");
    	Address address  = new Address("Bell Labs", "07974", "California", "USA");
    	Invanare invanare = new Invanare(pnr, name, Gender.MALE, address, "dennis.ritchie@belllabs.com", null); 
    	
    	invanareRepository.saveAndFlush(invanare);
    	return invanare;
    }

    private Bestallaradministrator createBestallaradministrator(){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator("Per Elofsson", "Handläggare", "LFC Nedre Dalarna", "010-1234567", "per.elofsson@fk.se");
    	
    	return bestallaradministrator;
    }   


    private Booking createBooking(){
    	
    	LocalDateTime start = new LocalDateTime();
    	LocalDateTime end = start.plusHours(1);
    	Party party = new HoSParty("Dr Hudson", "Surgeon", "Danderyds sjukhus");
    	Set<Party> set = new HashSet<Party>();
    	set.add(party);
    	Booking booking = new Booking(BookingType.EXAMINATION, start,end, set );
    	
    	
    	return booking;
    }

}
