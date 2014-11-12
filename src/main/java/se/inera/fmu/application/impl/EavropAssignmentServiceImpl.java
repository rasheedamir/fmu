package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropRejectedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Transactional
public class EavropAssignmentServiceImpl implements EavropAssignmentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final VardgivarenhetRepository vardgivarenhetRepository;
    private final DomainEventPublisher domainEventPublisher;

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropAssignmentServiceImpl(EavropRepository eavropRepository, VardgivarenhetRepository vardgivarenhetRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.vardgivarenhetRepository = vardgivarenhetRepository;
		this.domainEventPublisher = domainEventPublisher;
	}
	
	@Override
	public void assignEavropToVardgivarenhet(AssignEavropCommand aCommand) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getHsaId());

		if(eavrop.getCurrentAssignedVardgivarenhet()!=null){
			throw new IllegalArgumentException(String.format("Eavrop %s already assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), eavrop.getCurrentAssignedVardgivarenhet().getHsaId().toString() ));
		}
		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);;
		log.debug(String.format("Eavrop %s assigned to :: %s", aCommand.getEavropId().toString(), aCommand.getHsaId().toString()));
		
		handleEavropAssigned(aCommand.getEavropId(), aCommand.getHsaId());
	}

	@Override
	public void acceptEavropAssignment(AcceptEavropAssignmentCommand aCommand) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getHsaId());

		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), aCommand.getHsaId().toString() ));
		}
		eavrop.acceptEavropAssignment();
		log.debug(String.format("Eavrop %s accepted  by :: %s", aCommand.getEavropId().toString(), aCommand.getHsaId().toString()));
		
		handleEavropAccepted(aCommand.getEavropId(), aCommand.getHsaId());
	}

	@Override
	public void rejectEavropAssignment(RejectEavropAssignmentCommand aCommand)  throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getHsaId());

		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), aCommand.getHsaId().toString()));
		}
		eavrop.rejectEavropAssignment();
		log.debug(String.format("Eavrop %s rejected  by :: %s", aCommand.getEavropId().toString(), aCommand.getHsaId().toString()));
		
		handleEavropRejected(aCommand.getEavropId(), aCommand.getHsaId());
	}

	private Eavrop getEavropByEavropId(EavropId eavropId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}
	
	private Vardgivarenhet getVardgivarenhetByHsaId(HsaId hsaIdVardgivarenhet) throws EntityNotFoundException{
		Vardgivarenhet vardgivarenhet = this.vardgivarenhetRepository.findByHsaId(hsaIdVardgivarenhet);
		if(vardgivarenhet==null){
			throw new EntityNotFoundException(String.format("Vardgivarenhet %s not found", hsaIdVardgivarenhet.toString()));
		}
		return vardgivarenhet;
	}

	private DomainEventPublisher getDomainEventPublisher(){
		return this.domainEventPublisher;
	}
	
	private void handleEavropAssigned(EavropId eavropId, HsaId vardgivarenhetId){
		EavropAssignedToVardgivarenhetEvent event = new EavropAssignedToVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAssignedToVardgivarenhetEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleEavropAccepted(EavropId eavropId, HsaId vardgivarenhetId){
		EavropAcceptedByVardgivarenhetEvent event = new EavropAcceptedByVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAcceptedByVardgivarenhetEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}

	private void handleEavropRejected(EavropId eavropId, HsaId vardgivarenhetId){
		EavropRejectedByVardgivarenhetEvent event = new EavropRejectedByVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropRejectedByVardgivarenhetEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
	
	
}
