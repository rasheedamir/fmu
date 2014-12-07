package se.inera.fmu.application.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.Application;
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
import se.inera.fmu.domain.model.shared.Name;

/**
 * Test class for Repository.
 *
 *
 */
@SuppressWarnings("ALL")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@Transactional
public class ITLandstingRepositoryTest {
	
	@Inject
	private LandstingRepository landstingRepository;

	@Inject
	private LandstingssamordnareRepository landstingssamordnareRepository;
	
	@Inject
	private VardgivareRepository vardgivareRepository;

	@Inject
	private VardgivarenhetRepository vardgivarenhetRepository;

	private LandstingCode landstingCode;
    
    private HsaId vardgivareId;
    
    private HsaId vardgivarenhetId;
    
    private HsaId landstingssamordnarId;
    
    @Before
	public void setUp() throws Exception {
		this.landstingCode = new LandstingCode(99);
		Landsting landsting = createLandsting(this.landstingCode, "Stockholms Läns Landsting");

		this.landstingssamordnarId = new HsaId("SE160000000000-00000000A");
		Landstingssamordnare landstingssamordnare = createLandstingssamordnare(this.landstingssamordnarId, new Name("Sam", null, "Ordnarsson"), new HsaBefattning("S3", "Samordnare"), landsting, "sam@land.se");

		this.vardgivareId = new HsaId("SE160000000000-00000000B");
		Vardgivare vardgivare = createVardgivare(this.vardgivareId, "Personal Care AB");
		
		this.vardgivarenhetId = new HsaId("SE160000000000-00000000C");
		Vardgivarenhet vardgivarenhet = createVardgivarenhet(this.vardgivarenhetId, "Roinekliniken", new Address("Barnhusgatan 12", "33443", "Stockholm", "Sverige"),vardgivare, landsting);
		
	}
    
    @Test
    public void testLandsting(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	System.out.print(landsting);
    }

    @Test
    public void testLandstingssamordnareFromLandsting(){
    	Landsting landsting = landstingRepository.findByLandstingCode(this.landstingCode);
    	assertNotNull(landsting);
    	assertNotNull(landsting.getLandstingssamordnare());
    	assertEquals(1, landsting.getLandstingssamordnare().size());
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

    private Landstingssamordnare createLandstingssamordnare(HsaId hsaId, Name name, HsaBefattning hsaBefattning, Landsting landsting, String email){
    	Landstingssamordnare landstingssamordnare = new Landstingssamordnare(hsaId, name, hsaBefattning, landsting, email);
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
    
}
