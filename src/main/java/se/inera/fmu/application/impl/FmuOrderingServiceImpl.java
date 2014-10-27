package se.inera.fmu.application.impl;

import com.google.common.eventbus.AsyncEventBus;

import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;

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
    private final AsyncEventBus asyncEventBus;

    /**
     *
     * @param eavropRepository
     * @param invanareRepository
     * @param asyncEventBus
     */
    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository,
                                  final AsyncEventBus asyncEventBus) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
        this.asyncEventBus = asyncEventBus;
    }
    
    @Override
    public List<Eavrop> findAllUnassignedEavropByLandsting(Landsting landsting){
    	return this.eavropRepository.findAllByLandsting(landsting);
    }
    
//    private findAllEavropByLandstingAndStatus(){
//    	return this.eavropRepository.findAllByLandsting(landsting);
//    }

    /**
     *
     * @param arendeId
     * @param utredningType
     * @param interpreterLanguages
     * @param personalNumber
     * @param invanareName
     * @param invanareGender
     * @param invanareHomeAddress
     * @param invanareEmail
     * @param invanareSpecialNeeds
     * @param landsting
     * @param administratorName
     * @param administratorBefattning
     * @param administratorOrganisation
     * @param administratorPhone
     * @param administratorEmail
     * @return
     */
    @Override
    public ArendeId createNewEavrop(ArendeId arendeId,  UtredningType utredningType, String interpreterLanguages, PersonalNumber personalNumber,
                                    Name invanareName, Gender invanareGender, Address invanareHomeAddress,
                                    String invanareEmail, String invanareSpecialNeeds, Landsting landsting, String administratorName, 
                                    String administratorBefattning, String administratorOrganisation, String administratorPhone, 
                                    String administratorEmail) {
        
        Invanare invanare = createInvanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, invanareSpecialNeeds);
    	
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(administratorName, administratorBefattning, administratorOrganisation, administratorPhone, administratorEmail);
        
        Interpreter interpreter= new Interpreter(interpreterLanguages);
        
        log.debug(String.format("invanare created :: %s", invanare.toString()));
        
        EavropProperties props = getEavropProperties(); 
        
        Eavrop eavrop = EavropBuilder.eavrop()
		.withArendeId(arendeId)
		.withUtredningType(utredningType) 
		.withInvanare(invanare)
		.withLandsting(landsting)
		.withBestallaradministrator(bestallaradministrator)
		.withInterpreter(interpreter)
		.withEavropProperties(props)
		.build();

        //TODO: fix tolk setting, should there be a boolean with a language description string or only a string?
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

    /**
     *
     * @param personalNumber
     * @param invanareName
     * @param invanareGender
     * @param invanareHomeAddress
     * @param invanareEmail
     * @param specialNeeds
     * @return
     */
    private Invanare createInvanare(PersonalNumber personalNumber, Name invanareName, Gender invanareGender, Address invanareHomeAddress, String invanareEmail, String specialNeeds ){
    	Invanare invanare = new Invanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, specialNeeds);
    	invanare = invanareRepository.save(invanare);
        return invanare;
    }

    /**
     *
     * @param name
     * @param befattning
     * @param organisation
     * @param phone
     * @param email
     * @return
     */
    private Bestallaradministrator createBestallaradministrator(String name, String befattning, String organisation, String phone, String email){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator(name, befattning, organisation, phone, email);
    	//TODO: Set up repository, for this subclass or abstract superclass; 
//    	bestallaradministrator = bestallaradministrator.save(invanare);
    	return bestallaradministrator;
    }
    
    //TODO add call to fetch system parmeters from db 
    private EavropProperties getEavropProperties(){
    	return new EavropProperties(3,5,25,10);
    }
}
