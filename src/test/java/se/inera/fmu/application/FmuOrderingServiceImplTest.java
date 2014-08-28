package se.inera.fmu.application;

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
import se.inera.fmu.application.util.EavropUtil;
import se.inera.fmu.application.util.PatientUtil;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.patient.Patient;
import se.inera.fmu.domain.model.patient.PatientRepository;

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
    private PatientRepository patientRepository;

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    private FmuOrderingServiceImpl fmuOrderingService;

    @Before
    public void setUp() {
        fmuOrderingService = new FmuOrderingServiceImpl(eavropRepository, patientRepository);
        fmuOrderingService.setRuntimeService(activitiRule.getRuntimeService());
        fmuOrderingService.setTaskService(activitiRule.getTaskService());
    }

    @Test
    @Deployment(resources = {"processes/fmu.bpmn"})
    public void shouldCreateNewEavrop() {
        final Eavrop savedEavrop = stubRepositoryToReturnEavropOnSave();
        final Patient savedPatient = stubRepositoryToReturnPatientOnSave();
        final ArendeId arendeId = fmuOrderingService.createNewEavrop(EavropUtil.ARENDE_ID, EavropUtil.UTREDNING_TYPE,
                                                                         EavropUtil.TOLK, PatientUtil.PERSONAL_NUMBER,
                                                                         PatientUtil.NAME, PatientUtil.GENDER,
                                                                         PatientUtil.HOME_ADDRESS, PatientUtil.EMAIL);
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

    private Patient stubRepositoryToReturnPatientOnSave() {
        Patient patient = PatientUtil.createPatient();
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        return patient;
    }
}
