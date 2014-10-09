package se.inera.fmu.application;

import com.google.common.eventbus.AsyncEventBus;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.fmu.application.impl.FmuOrderingServiceImpl;
import se.inera.fmu.application.util.BestallaradministratorUtil;
import se.inera.fmu.application.util.EavropUtil;
import se.inera.fmu.application.util.InvanareUtil;
import se.inera.fmu.application.util.LandstingUtil;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Rasheed on 7/7/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class FmuOrderingServiceImplTest {

    @Mock
    private EavropRepository eavropRepository;

    @Mock
    private InvanareRepository patientRepository;

    @Mock
    private AsyncEventBus asyncEventBus;

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    private FmuOrderingServiceImpl fmuOrderingService;

    @Before
    public void setUp() {
        fmuOrderingService = new FmuOrderingServiceImpl(eavropRepository, patientRepository, asyncEventBus);
        fmuOrderingService.setRuntimeService(activitiRule.getRuntimeService());
        fmuOrderingService.setTaskService(activitiRule.getTaskService());
    }

    @Test
    @Deployment(resources = {"processes/fmu.bpmn"})
    public void shouldCreateNewEavrop() {
        final Eavrop savedEavrop = stubRepositoryToReturnEavropOnSave();
        final Invanare savedPatient = stubRepositoryToReturnPatientOnSave();
        final ArendeId arendeId = fmuOrderingService.createNewEavrop(EavropUtil.ARENDE_ID, EavropUtil.UTREDNING_TYPE,
                                                                         EavropUtil.TOLK, InvanareUtil.PERSONAL_NUMBER,
                                                                         InvanareUtil.NAME, InvanareUtil.GENDER,
                                                                         InvanareUtil.HOME_ADDRESS, InvanareUtil.EMAIL, InvanareUtil.SPECIAL_NEED,
                                                                         LandstingUtil.createLandsting(), BestallaradministratorUtil.NAME, 
                                                                         BestallaradministratorUtil.BEFATTNING, BestallaradministratorUtil.ORGANISATION, 
                                                                         BestallaradministratorUtil.PHONE, BestallaradministratorUtil.EMAIL);
        
        // verify repository's were called
        verify(patientRepository, times(1)).save(savedPatient);
        verify(eavropRepository, times(1)).save(savedEavrop);
        assertEquals("Returned ArendeId should come from the repository", savedEavrop.getArendeId(), arendeId);

        // verify a business process has been started
        // check if the process is started
        ProcessInstance processInstance = activitiRule.getRuntimeService().createProcessInstanceQuery()
                                        .processInstanceBusinessKey(arendeId.toString())
                                        .singleResult();
        assertNotNull(processInstance);

        // check if the eavrop JPA-entity is available
        Eavrop eavropFromVariable = (Eavrop) activitiRule.getRuntimeService().getVariable(processInstance.getId(), "eavrop");
        assertNotNull(eavropFromVariable);
    }

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
}
