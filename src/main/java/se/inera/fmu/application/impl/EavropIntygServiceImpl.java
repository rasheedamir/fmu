package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropIntygService;
import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplemetsRequestedFromBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Transactional
public class EavropIntygServiceImpl implements EavropIntygService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropIntygServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public void addIntygSentInformation(AddIntygSentCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		HoSPerson person = aCommand.getIntygSentBy();
		IntygSentInformation intygSentInformation = new IntygSentInformation(aCommand.getIntygSentDateTime(),person);
		
		eavrop.addIntygSentInformation(intygSentInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSentInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygSent(eavrop.getEavropId(), aCommand.getIntygSentDateTime());
	}

	@Override
	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		IntygComplementRequestInformation intygComplementRequestInformation = new IntygComplementRequestInformation(aCommand.getIntygComplementRequestDateTime(),person);
		
		eavrop.addIntygComplementRequestInformation(intygComplementRequestInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygComplementRequestInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygComplementRequest(eavrop.getEavropId(), aCommand.getIntygComplementRequestDateTime());
	}

	@Override
	public void addIntygApprovedInformation(AddIntygApprovedCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		IntygApprovedInformation intygApprovedInformation = new IntygApprovedInformation(aCommand.getIntygApprovedDateTime(),person);
		
		eavrop.addIntygApprovedInformation(intygApprovedInformation);
		
		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygApprovedInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygApproved(eavrop.getEavropId(), aCommand.getIntygApprovedDateTime());
		
	}

	private Eavrop getEavropByArendeId(ArendeId arendeId){
		Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop with ArendeId %s not found", arendeId.toString()));
		}
		return eavrop;
	}
	
	private DomainEventPublisher getDomainEventPublisher(){
		return this.domainEventPublisher;
	}
	
	private void handleIntygApproved(EavropId eavropId, DateTime approvedDateTime){
		IntygApprovedByBestallareEvent event = new IntygApprovedByBestallareEvent(eavropId, approvedDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygApprovedByBestallareEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleIntygComplementRequest(EavropId eavropId, DateTime complementRequestDateTime){
		IntygComplemetsRequestedFromBestallareEvent event = new IntygComplemetsRequestedFromBestallareEvent(eavropId, complementRequestDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygComplemetsRequestedFromBestallareEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleIntygSent(EavropId eavropId, DateTime intygSentDateTime){
		IntygSentEvent event = new IntygSentEvent(eavropId, intygSentDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSentEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
}
