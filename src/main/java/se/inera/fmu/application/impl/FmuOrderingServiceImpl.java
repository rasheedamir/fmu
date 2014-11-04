package se.inera.fmu.application.impl;

import com.google.common.eventbus.AsyncEventBus;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rasheed on 7/7/14.
 *
 * Application Service for managing FMU process
 */
@SuppressWarnings("all")
@Service
@Validated
@Transactional
@Slf4j
public class FmuOrderingServiceImpl implements FmuOrderingService {

    private final EavropRepository eavropRepository;
    private final InvanareRepository invanareRepository;
    private final AsyncEventBus asyncEventBus;
    private final LandstingRepository landstingRepository;
    private final CurrentUserService currentUserService;

    /**
     *
     * @param eavropRepository
     * @param invanareRepository
     * @param asyncEventBus
     */
    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository,
                                  final AsyncEventBus asyncEventBus, final LandstingRepository landstingRepository, final CurrentUserService currentUser) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
        this.asyncEventBus = asyncEventBus;
        this.landstingRepository = landstingRepository;
        this.currentUserService = currentUser;
    }
    
    @Override
    public List<Eavrop> findAllUnassignedEavropByLandsting(Landsting landsting){
    	return this.eavropRepository.findAllByLandsting(landsting);
    }
    
//    private findAllEavropByLandstingAndStatus(){
//    	return this.eavropRepository.findAllByLandsting(landsting);
//    }

    /**
     * Creates a an eavrop.
     *
     * @param aCommand : CreateEavropCommand
     * @return arendeId
     */
    @Override
    public ArendeId createEavrop(CreateEavropCommand aCommand) {
        
        Invanare invanare = createInvanare(aCommand.getPersonalNumber(), aCommand.getInvanareName(), aCommand.getInvanareGender(),
                aCommand.getInvanareHomeAddress(), aCommand.getInvanareEmail(), aCommand.getInvanareSpecialNeeds());
    	
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(aCommand.getAdministratorName(),
                aCommand.getAdministratorBefattning(), aCommand.getAdministratorOrganisation(),
                aCommand.getAdministratorPhone(), aCommand.getAdministratorEmail());
        
        Interpreter interpreter= new Interpreter(aCommand.getInterpreterLanguages());
        
        EavropProperties props = getEavropProperties();
        
        Eavrop eavrop = EavropBuilder.eavrop()
		.withArendeId(aCommand.getArendeId())
		.withUtredningType(aCommand.getUtredningType())
		.withInvanare(invanare)
		.withLandsting(aCommand.getLandsting())
		.withBestallaradministrator(bestallaradministrator)
		.withInterpreter(interpreter)
		.withEavropProperties(props)
		.build();

        //TODO: fix tolk setting, should there be a boolean with a language description string or only a string?
        eavrop = eavropRepository.save(eavrop);

        log.debug(String.format("invanare created :: %s", invanare));
        log.debug(String.format("bestallaradministrator created :: %s", bestallaradministrator));
        log.debug(String.format("eavrop created :: %s", eavrop));

        //Publish an event to notify the interested listeners/subscribers that an eavrop has been created.
        asyncEventBus.post(new EavropCreatedEvent(eavrop.getArendeId()));

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
    private Invanare createInvanare(PersonalNumber personalNumber, Name invanareName, Gender invanareGender, Address invanareHomeAddress,
                                    String invanareEmail, String specialNeeds ){
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

    private EavropProperties getEavropProperties(){
    	return new EavropProperties(3,5,25,10);
    }

	@Override
	public List<Eavrop> getOverviewEavrops() {
		User currentUSer = this.currentUserService.getCurrentUser();
		switch (currentUSer.getActiveRole()) {
		case LANDSTINGSSAMORDNARE:
			System.out.println("landstingSamordnare user");
			return this.eavropRepository.findAll();
		case UTREDARE:
			System.out.println("Utredare user");
			return this.eavropRepository.findAll();
		default:
			System.out.println("Unauthorized user");
			return new ArrayList<Eavrop>();
		}
	}
}