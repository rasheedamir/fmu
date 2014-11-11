package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropIntygDomainService;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplemetsRequestedFromBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.person.Bestallaradministrator;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Transactional
public class EavropIntygDomainServiceImpl implements EavropIntygDomainService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropIntygDomainServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public void addIntygSignedInformation(AddIntygSignedCommand aCommand) {
		validateAddIntygSignedCommand(aCommand);
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName())){
			person  = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		}
		
		IntygSignedInformation intygSignedInformation = new IntygSignedInformation(aCommand.getIntygSignedDateTime(),person);
		
		eavrop.addIntygSignedInformation(intygSignedInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSignedInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygSigned(eavrop.getEavropId(), aCommand.getIntygSignedDateTime());
	}

	@Override
	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand aCommand) {
		validateAddIntygComplementRequestCommand(aCommand);
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName())){
			person  = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		}
		
		IntygComplementRequestInformation intygComplementRequestInformation = new IntygComplementRequestInformation(aCommand.getIntygComplementRequestDateTime(),person);
		
		eavrop.addIntygComplementRequestInformation(intygComplementRequestInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygComplementRequestInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygComplementRequest(eavrop.getEavropId(), aCommand.getIntygComplementRequestDateTime());
	}

	@Override
	public void addIntygApprovedInformation(AddIntygApprovedCommand aCommand) {
		validateAddIntygApprovedCommand(aCommand);
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName())){
			person  = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		}
		
		IntygApprovedInformation intygApprovedInformation = new IntygApprovedInformation(aCommand.getIntygApprovedDateTime(),person);
		
		eavrop.addIntygApprovedInformation(intygApprovedInformation);
		
		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygApprovedInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleIntygApproved(eavrop.getEavropId(), aCommand.getIntygApprovedDateTime());
		
	}

	private void validateAddIntygSignedCommand(AddIntygSignedCommand command){
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getIntygSignedDateTime());
	}

	private void validateAddIntygComplementRequestCommand(AddIntygComplementRequestCommand command){
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getIntygComplementRequestDateTime());
	}

	private void validateAddIntygApprovedCommand(AddIntygApprovedCommand command){
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getIntygApprovedDateTime());
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

	private void handleIntygSigned(EavropId eavropId, DateTime intygSignedDateTime){
		IntygSignedEvent event = new IntygSignedEvent(eavropId, intygSignedDateTime);
        if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSignedEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
}
