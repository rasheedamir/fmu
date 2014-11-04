package se.inera.fmu.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.inera.fmu.application.impl.CreateEavropCommand;
import se.inera.fmu.application.impl.FmuOrderingServiceImpl;
import se.inera.fmu.application.util.BestallaradministratorUtil;
import se.inera.fmu.application.util.EavropUtil;
import se.inera.fmu.application.util.InvanareUtil;
import se.inera.fmu.application.util.LandstingUtil;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
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


    private FmuOrderingServiceImpl fmuOrderingService;

    @Before
    public void setUp() {
        fmuOrderingService = new FmuOrderingServiceImpl(eavropRepository, patientRepository, configuration, asyncEventBus, null, null);
    }

    @Test
    public void shouldCreateNewEavrop() {
        final Eavrop savedEavrop = stubRepositoryToReturnEavropOnSave();
        final Invanare savedPatient = stubRepositoryToReturnPatientOnSave();
        final ArendeId arendeId = fmuOrderingService.createEavrop(new CreateEavropCommand(EavropUtil.ARENDE_ID, EavropUtil.UTREDNING_TYPE,
                                                                        EavropUtil.TOLK, InvanareUtil.PERSONAL_NUMBER,
                                                                        InvanareUtil.NAME, InvanareUtil.GENDER,
                                                                        InvanareUtil.HOME_ADDRESS, InvanareUtil.EMAIL, InvanareUtil.SPECIAL_NEED,
                                                                        LandstingUtil.createLandsting(), BestallaradministratorUtil.NAME,
                                                                        BestallaradministratorUtil.BEFATTNING, BestallaradministratorUtil.ORGANISATION,
                                                                        BestallaradministratorUtil.UNIT, BestallaradministratorUtil.PHONE, BestallaradministratorUtil.EMAIL));
        
        // verify repository's were called
        verify(patientRepository, times(1)).save(savedPatient);
        verify(eavropRepository, times(1)).save(savedEavrop);
        assertEquals("Returned ArendeId should come from the repository", savedEavrop.getArendeId(), arendeId);
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