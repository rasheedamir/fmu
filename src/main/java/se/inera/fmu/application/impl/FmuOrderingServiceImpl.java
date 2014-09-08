package se.inera.fmu.application.impl;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Gender;
import se.inera.fmu.domain.model.patient.Name;
import se.inera.fmu.domain.model.patient.Patient;
import se.inera.fmu.domain.model.patient.PatientRepository;

import javax.inject.Inject;

import java.util.HashMap;

/**
 * Created by Rasheed on 7/7/14.
 *
 * Application Service for managing FMU process
 */
@SuppressWarnings("ALL")
@Service
@Validated
@Transactional
public class FmuOrderingServiceImpl extends AbstractServiceImpl implements FmuOrderingService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EavropRepository eavropRepository;
    private final PatientRepository patientRepository;

    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final PatientRepository patientRepository) {
        this.eavropRepository = eavropRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public ArendeId createNewEavrop(ArendeId arendeId, UtredningType utredningType, String tolk, String personalNumber,
                                    Name patientName, Gender patientGender, Address patientHomeAddress,
                                    String patientEmail) {
        Patient patient = new Patient(personalNumber, patientName, patientGender, patientHomeAddress, patientEmail);
        patient = patientRepository.save(patient);
        log.debug(String.format("patient created :: %s", patient.toString()));

        Eavrop eavrop = new Eavrop(arendeId, utredningType, tolk, patient);
        eavrop = eavropRepository.save(eavrop);
        log.debug(String.format("eavrop created :: %s", eavrop));

        String businessKey = eavrop.getArendeId().toString();

        // Also set the eavrop as process-variable
        HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put("eavrop", eavrop);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("fmuProcess", businessKey, variables);
        log.debug(String.format("business process started :: %s", processInstance.getId()));

        return eavrop.getArendeId();
    }

}
