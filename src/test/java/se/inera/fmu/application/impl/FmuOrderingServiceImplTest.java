package se.inera.fmu.application.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.application.util.BestallaradministratorUtil;
import se.inera.fmu.application.util.EavropUtil;
import se.inera.fmu.application.util.InvanareUtil;
import se.inera.fmu.application.util.LandstingUtil;
import se.inera.fmu.application.util.PriorMedicalExaminationUtil;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.systemparameter.Configuration;

import com.google.common.eventbus.AsyncEventBus;

/**
 * Created by Rasheed on 7/7/14.
 *
 * Unit tests for FmuOrderingServiceImpl.
 */
@RunWith(MockitoJUnitRunner.class)
public class FmuOrderingServiceImplTest {

	@Mock
    private EavropRepository eavropRepository;

    @Mock
    private InvanareRepository patientRepository;

    @Mock
    private AsyncEventBus asyncEventBus;
    
    @Mock
    private Configuration configuration;
    
    @Mock
    private CurrentUserService currentUserService;
    
    @Mock
    private LandstingRepository landstingRepository;
    
    @Mock
    private VardgivarenhetRepository vgRepository;
    
    @Mock
    private DomainEventPublisher eventPublisher;


    private FmuOrderingServiceImpl fmuOrderingService;

    @Before
    public void setUp() {
        fmuOrderingService = new FmuOrderingServiceImpl(eavropRepository, patientRepository, configuration, eventPublisher, landstingRepository, currentUserService, vgRepository, null, null, null);
    }

    /*
        @Test
        public void shouldCreateNewEavrop() {
            final Eavrop savedEavrop = stubRepositoryToReturnEavropOnSave();
            final Invanare savedPatient = stubRepositoryToReturnPatientOnSave();
            final ArendeId arendeId = fmuOrderingService.createEavrop(new CreateEavropCommand(
                    EavropUtil.ARENDE_ID, EavropUtil.UTREDNING_TYPE,  EavropUtil.TOLK, InvanareUtil.PERSONAL_NUMBER, InvanareUtil.NAME,
                    InvanareUtil.GENDER, InvanareUtil.HOME_ADDRESS, InvanareUtil.PHONE, InvanareUtil.EMAIL, InvanareUtil.SPECIAL_NEED, LandstingUtil.createLandsting(),
                    BestallaradministratorUtil.NAME, BestallaradministratorUtil.BEFATTNING, BestallaradministratorUtil.ORGANISATION,
                    BestallaradministratorUtil.UNIT, BestallaradministratorUtil.PHONE, BestallaradministratorUtil.EMAIL, EavropUtil.DESCRIPTION,
                    EavropUtil.UTREDNING_FOCUS, EavropUtil.ADDITIONAL_INFORMATION, PriorMedicalExaminationUtil.EXAMINED_AT,
                    PriorMedicalExaminationUtil.MEDICAL_LEAVE_ISSUED_AT, PriorMedicalExaminationUtil.NAME, PriorMedicalExaminationUtil.ROLE,
                    PriorMedicalExaminationUtil.ORGANISATION, PriorMedicalExaminationUtil.UNIT, PriorMedicalExaminationUtil.PHONE,
                    PriorMedicalExaminationUtil.EMAIL));

            // verify repository's were called
            verify(patientRepository, times(1)).save(savedPatient);
            verify(eavropRepository, times(1)).save(savedEavrop);
            assertEquals("Returned ArendeId should come from the repository", savedEavrop.getArendeId(), arendeId);
        }
    */

    private Eavrop stubRepositoryToReturnEavropOnSave() {
        Eavrop eavrop = EavropUtil.createEavrop();
        when(eavropRepository.save(any(Eavrop.class))).thenReturn(eavrop);
        return eavrop;
    }

    private Invanare stubRepositoryToReturnPatientOnSave() {
        Invanare patient = InvanareUtil.createInvanare();
        when(patientRepository.save(any(Invanare.class))).thenReturn(patient);
        return patient;
    }
    
    @Test
    public void getEavropForSamordnare(){
    	User currentUser = new User();
    	final int landstingCode = 100;
		currentUser.setLandstingCode(landstingCode);
    	currentUser.getRoles().add(Role.LANDSTINGSSAMORDNARE);
    	currentUser.setActiveRole(Role.LANDSTINGSSAMORDNARE);
    	
    	Landsting lt = new Landsting(new LandstingCode(landstingCode), "Test landsting");
    	
    	when(landstingRepository.findByLandstingCode(new LandstingCode(landstingCode))).thenReturn(lt);
    	when(currentUserService.getCurrentUser()).thenReturn(currentUser);
    	final String id = "ABC";
		fmuOrderingService.getEavropForUser(new EavropId(id));
		
		verify(currentUserService).getCurrentUser();
		verify(landstingRepository).findByLandstingCode(new LandstingCode(landstingCode));
		verify(eavropRepository).findByEavropIdAndLandsting(new EavropId(id), lt);
    }
    
    @Test
    public void getEavropForUtredare(){
    	User currentUser = new User();
		final String vardenhetHsaId = "SE1111112222-AAAA";
		currentUser.setVardenhetHsaId(vardenhetHsaId);
    	currentUser.getRoles().add(Role.UTREDARE);
    	currentUser.setActiveRole(Role.UTREDARE);
    	
    	Vardgivarenhet ve = new Vardgivarenhet(new Vardgivare(new HsaId("SE2222223333-BBBB"), "test vg"), new HsaId(vardenhetHsaId), "Test unit", null);
    	
    	when(vgRepository.findByHsaId(new HsaId(vardenhetHsaId))).thenReturn(ve);
    	when(currentUserService.getCurrentUser()).thenReturn(currentUser);
    	final String id = "ABC";
		fmuOrderingService.getEavropForUser(new EavropId(id));
		
		verify(currentUserService).getCurrentUser();
		verify(vgRepository).findByHsaId(new HsaId(vardenhetHsaId));
		verify(eavropRepository).findByEavropIdAndVardgivare(new EavropId(id), ve);
    }
}
