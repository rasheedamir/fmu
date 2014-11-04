package se.inera.fmu.application.impl;

import com.google.common.eventbus.AsyncEventBus;

import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.validator.internal.util.privilegedactions.GetConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.model.systemparameter.Configuration;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

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
    private final Configuration configuration;

    /**
     *
     * @param eavropRepository
     * @param invanareRepository
     * @param asyncEventBus
     */
    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository,
                                  final AsyncEventBus asyncEventBus, final Configuration configuration) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
        this.asyncEventBus = asyncEventBus;
        this.configuration = configuration;
    }
    
	private AsyncEventBus getEventBus(){
		return this.asyncEventBus;
	}

    

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
                                    String administratorBefattning, String administratorOrganisation, String administratorEnhet, String administratorPhone, 
                                    String administratorEmail) {
        
        Invanare invanare = createInvanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, invanareSpecialNeeds);
    	
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(administratorName, administratorBefattning, administratorOrganisation, administratorEnhet, administratorPhone, administratorEmail);
        
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

        handleEavropCreated(eavrop.getEavropId());
        
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
    private Bestallaradministrator createBestallaradministrator(String name, String befattning, String organisation, String enhet, String phone, String email){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator(name, befattning, organisation, enhet, phone, email);
    	//TODO: Set up repository, for this subclass or abstract superclass; 
//    	bestallaradministrator = bestallaradministrator.save(invanare);
    	return bestallaradministrator;
    }
    
    
	//Event handling methods
	private void handleEavropCreated(Long eavropId){
		EavropCreatedEvent event = new EavropCreatedEvent(eavropId);
		getEventBus().post(event);
	}
     
    private EavropProperties getEavropProperties(){
    	int startDateOffset = getConfiguration().getInteger(Configuration.KEY_EAVROP_START_DATE_OFFSET, 3);    	
    	int acceptanceValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_ACCEPTANCE_VALID_LENGTH, 5);
    	int assessmentValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_ASSESSMENT_VALID_LENGTH, 25);
    	int completionValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_COMPLETION_VALID_LENGTH, 10);
    	
    	return new EavropProperties(startDateOffset, acceptanceValidLength, assessmentValidLength, completionValidLength);
    }
    
    private Configuration getConfiguration(){
    	return this.configuration;
    }
}
