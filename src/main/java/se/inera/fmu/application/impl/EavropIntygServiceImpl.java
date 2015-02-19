package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropIntygService;
import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * 
 * @see EavropIntygService
 *
 */
@Service
@Validated
@Slf4j
public class EavropIntygServiceImpl implements EavropIntygService {

    private final EavropRepository eavropRepository;

    /**
     * Constructor
     * @param eavropRepository
     */
	@Inject
	public EavropIntygServiceImpl(EavropRepository eavropRepository) {
		this.eavropRepository = eavropRepository;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId addIntygSentInformation(AddIntygSentCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		HoSPerson person = aCommand.getIntygSentBy();
		IntygSentInformation intygSentInformation = new IntygSentInformation(aCommand.getIntygSentDateTime(),person);
		
		eavrop.addIntygSentInformation(intygSentInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygSentInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		return eavrop.getEavropId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId addIntygComplementRequestInformation(AddIntygComplementRequestCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		IntygComplementRequestInformation intygComplementRequestInformation = new IntygComplementRequestInformation(aCommand.getIntygComplementRequestDateTime(),person);
		
		eavrop.addIntygComplementRequestInformation(intygComplementRequestInformation);

		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygComplementRequestInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		return eavrop.getEavropId();
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId addIntygApprovedInformation(AddIntygApprovedCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		IntygApprovedInformation intygApprovedInformation = new IntygApprovedInformation(aCommand.getIntygApprovedDateTime(),person);
		
		eavrop.addIntygApprovedInformation(intygApprovedInformation);
		
		if(log.isDebugEnabled()){
        	log.debug(String.format("IntygApprovedInformation added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		return eavrop.getEavropId();
	}

	
	private Eavrop getEavropByArendeId(ArendeId arendeId){
		Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop with ArendeId %s not found", arendeId.toString()));
		}
		return eavrop;
	}
}
