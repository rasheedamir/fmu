package se.inera.fmu.application.impl;

import com.google.common.eventbus.AsyncEventBus;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.PageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivareRepository;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.model.systemparameter.Configuration;

import javax.inject.Inject;

import java.util.ArrayList;
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
    private final Configuration configuration;
    private final LandstingRepository landstingRepository;
    private final CurrentUserService currentUserService;
    private final VardgivarenhetRepository vardgivarEnhetRepository;

    /**
     *
     * @param eavropRepository
     * @param invanareRepository
     * @param asyncEventBus
     */
    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository,
    							  final Configuration configuration, final AsyncEventBus asyncEventBus, 
    							  final LandstingRepository landstingRepository, final CurrentUserService currentUser, 
    							  final VardgivarenhetRepository vardgivarEnhetRepository) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
        this.configuration = configuration;
        this.asyncEventBus = asyncEventBus;
        this.landstingRepository = landstingRepository;
        this.currentUserService = currentUser;
        this.vardgivarEnhetRepository = vardgivarEnhetRepository;
    }
    
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
                aCommand.getAdministratorBefattning(), aCommand.getAdministratorOrganisation(), aCommand.getAdministratorEnhet(),
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
        
        eavrop = eavropRepository.save(eavrop);
        
        log.debug(String.format("invanare created :: %s", invanare));
        log.debug(String.format("bestallaradministrator created :: %s", bestallaradministrator));
        log.debug(String.format("eavrop created :: %s", eavrop));

        //Publish an event to notify the interested listeners/subscribers that an eavrop has been created.
        asyncEventBus.post(new EavropCreatedEvent(eavrop.getEavropId()));

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
    private Bestallaradministrator createBestallaradministrator(String name, String befattning, String organisation, String unit, String phone, String email){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator(name, befattning, organisation, unit, phone, email);
    	//TODO: Set up repository, for this subclass or abstract superclass; 
//    	bestallaradministrator = bestallaradministrator.save(invanare);
    	return bestallaradministrator;
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

	@Override
	public Page<Eavrop> getOverviewEavrops(long fromDate, long toDate, EavropStateType state, Pageable paginationSpecs) {
		User currentUSer = this.currentUserService.getCurrentUser();
		List<EavropState> states = new ArrayList<EavropState>();
		
		switch (currentUSer.getActiveRole()) {
		case LANDSTINGSSAMORDNARE:
			if(currentUSer.getLandstingCode() == null)
				return null;
			LocalDate startDate = new LocalDate(fromDate);
			LocalDate endDate = new LocalDate(toDate);
			
			Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(currentUSer.getLandstingCode()));
			return this.eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, startDate, endDate, null, paginationSpecs);
		case UTREDARE:
			if(currentUSer.getVardenhetHsaId() == null) {
				return null;
			}
		default:
			return null;
		}
	}
	
	protected Eavrop getEavropForUser(EavropId id){
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop result = null;
		
		if(currentUser.getActiveRole() == Role.LANDSTINGSSAMORDNARE){
			Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(currentUser.getLandstingCode()));
			result = this.eavropRepository.findByEavropIdAndLandsting(id, landsting);
		} else if(currentUser.getActiveRole() == Role.UTREDARE){
			Vardgivarenhet ve = this.vardgivarEnhetRepository.findByHsaId(new HsaId(currentUser.getVardenhetHsaId()));
			result = this.eavropRepository.findByEavropIdAndVardgivare(id, ve);
		} else {
			throw new IllegalStateException("User has no active role");
		}
		
		return result;
	}
	
	
}
