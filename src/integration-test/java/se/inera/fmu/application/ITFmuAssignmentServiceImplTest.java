package se.inera.fmu.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.constraints.AssertFalse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.inera.fmu.Application;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.Interpreter;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.TestUtil;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;

/**
 * Created by Rasheed on 8/8/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@IntegrationTest("server.port:0")
@Slf4j
public class ITFmuAssignmentServiceImplTest {
	
	@Inject
	private CurrentUserService currentUserService;
	
	@Inject
	private FmuOrderingService fmuOrderingService;
	
	@Inject
	private EavropAssignmentService eavropAssignmentService;
	
	@Inject
	private FmuListService fmuListService;
	
	@Before
	public void setUp(){
		TestUtil.loginWithNoActiveRole();
	}
	
	@Test
	public void testAssignment(){
		User currentUser = this.currentUserService.getCurrentUser();
		
		ArendeId TST_1 = new ArendeId("010000000001");
		ArendeId TST_2 = new ArendeId("010000000002");
		
		Landsting landsting =  fmuListService.findLandstingByLandstingCode(new LandstingCode(1));
		assertNotEquals(landsting.getVardgivarenheter(), null);
		assertEquals(landsting.getVardgivarenheter().isEmpty(), Boolean.FALSE);
		Vardgivarenhet vardgivarenhet = landsting.getVardgivarenheter().iterator().next();

		//Create first Eavrop
		fmuOrderingService.createEavrop(createEavropCommand(TST_1));
		Eavrop eavrop_TST_1 = fmuListService.findByArendeId(TST_1);
		assertNotEquals(null,eavrop_TST_1);
		assertTrue(EavropStateType.UNASSIGNED.equals(eavrop_TST_1.getStatus()));
		//Assign first Eavrop
		AssignEavropCommand assignEavrop_TST_1 = new AssignEavropCommand(eavrop_TST_1.getEavropId(), vardgivarenhet.getHsaId(), new HsaId(currentUser.getHsaId()),"A", "B", "C","D" );
		eavropAssignmentService.assignEavropToVardgivarenhet(assignEavrop_TST_1);
		eavrop_TST_1 = fmuListService.findByArendeId(TST_1);
		assertNotEquals(null,eavrop_TST_1);
		assertTrue(EavropStateType.ASSIGNED.equals(eavrop_TST_1.getStatus()));
		
		//Create second Eavrop
		fmuOrderingService.createEavrop(createEavropCommand(TST_2));
		Eavrop eavrop_TST_2 = fmuListService.findByArendeId(TST_2);
		assertNotEquals(eavrop_TST_1, null);
		assertTrue(EavropStateType.UNASSIGNED.equals(eavrop_TST_2.getStatus()));
		//Assign second Eavrop
		AssignEavropCommand assignEavrop_TST_2 = new AssignEavropCommand(eavrop_TST_2.getEavropId(), vardgivarenhet.getHsaId(), new HsaId(currentUser.getHsaId()),"A", "B", "C","D" );
		eavropAssignmentService.assignEavropToVardgivarenhet(assignEavrop_TST_2);
		eavrop_TST_2 = fmuListService.findByArendeId(TST_2);
		assertNotEquals(null,eavrop_TST_2);
		assertTrue(EavropStateType.ASSIGNED.equals(eavrop_TST_2.getStatus()));
		
		//Accept both eavrop
		AcceptEavropAssignmentCommand accept_TST_1 = new AcceptEavropAssignmentCommand(eavrop_TST_1.getEavropId(), vardgivarenhet.getHsaId(), new HsaId(currentUser.getHsaId()),"A", "B", "C","D");
		eavropAssignmentService.acceptEavropAssignment(accept_TST_1);
		AcceptEavropAssignmentCommand accept_TST_2 = new AcceptEavropAssignmentCommand(eavrop_TST_2.getEavropId(), vardgivarenhet.getHsaId(), new HsaId(currentUser.getHsaId()),"A", "B", "C","D");
		eavropAssignmentService.acceptEavropAssignment(accept_TST_2);
	
		eavrop_TST_1 = fmuListService.findByArendeId(TST_1);
		eavrop_TST_2 = fmuListService.findByArendeId(TST_2);
		assertNotEquals(null,eavrop_TST_1);
		assertTrue(EavropStateType.ACCEPTED.equals(eavrop_TST_1.getStatus()));
		assertNotEquals(null,eavrop_TST_2);
		assertTrue(EavropStateType.ACCEPTED.equals(eavrop_TST_2.getStatus()));
		
	}

	
    private CreateEavropCommand createEavropCommand(ArendeId arendeId){
        UtredningType utredningType = UtredningType.TMU;
        String description = null;
        String utredningFocus = null;
        String additionalInformation = null;
        Interpreter interpreter = null;
        Invanare invanare = createInvanare();
        LandstingCode landstingCode  = new LandstingCode(1);
        Bestallaradministrator bestallaradministrator =  createAdministrator();
        PriorMedicalExamination priorMedicalExamination = null;

    	
    	return new CreateEavropCommand(arendeId,utredningType, description, utredningFocus, additionalInformation, 
    								   interpreter, invanare, landstingCode, bestallaradministrator, priorMedicalExamination);
    }
    
    
    private Bestallaradministrator createAdministrator() {
		return new Bestallaradministrator("D", "E", "F","H","I","j@k.se");
	}


	private Invanare createInvanare(){
    	PersonalNumber personalNumber = new PersonalNumber("1901-01-01");;
    	Name name = new Name("A",null,"B"); 
    	Gender gender= Gender.FEMALE; 
    	Address homeAddress = new Address("C", "12345","","Sweden"); 
    	String phone= "12334"; 
    	String email= "sdwsd@s.se"; 
    	String specialNeeds = null;
    	return new Invanare(personalNumber, name, gender, homeAddress, phone, email, specialNeeds);
    }
}
