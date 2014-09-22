package se.inera.fmu.application.impl;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.invanare.Invanare;
import se.inera.fmu.domain.model.invanare.InvanareRepository;
import se.inera.fmu.domain.model.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.party.Bestallaradministrator;

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
    private final InvanareRepository invanareRepository;

    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
    }

    @Override
    public ArendeId createNewEavrop(ArendeId arendeId,  UtredningType utredningType, String tolk, PersonalNumber personalNumber,
                                    Name invanareName, Gender invanareGender, Address invanareHomeAddress,
                                    String invanareEmail, String invanareSpecialNeeds, Landsting landsting, String administratorName, 
                                    String administratorBefattning, String administratorOrganisation, String administratorPhone, 
                                    String administratorEmail  ) {
        
        Invanare invanare = createInvanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, invanareSpecialNeeds);
    	
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(administratorName, administratorBefattning, administratorOrganisation, administratorPhone, administratorEmail);
        
        log.debug(String.format("invanare created :: %s", invanare.toString()));

        Eavrop eavrop = new Eavrop(arendeId, utredningType, invanare, landsting, bestallaradministrator);
        
        //TODO: fix tolk setting, should there be a boolean with a language description string or only a string?
        eavrop.setTolk(true);
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
    
    private Invanare createInvanare(PersonalNumber personalNumber, Name invanareName, Gender invanareGender, Address invanareHomeAddress, String invanareEmail, String specialNeeds ){
    	Invanare invanare = new Invanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, specialNeeds);
    	invanare = invanareRepository.save(invanare);
        return invanare;
    }
    
    private Bestallaradministrator createBestallaradministrator(String name, String befattning, String organisation, String phone, String email){
    	
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator(name, befattning, organisation, phone, email);
    	//TODO: Set up repository, for this subclass or abstract superclass; 
//    	bestallaradministrator = bestallaradministrator.save(invanare);
    	return bestallaradministrator;
    }
}
