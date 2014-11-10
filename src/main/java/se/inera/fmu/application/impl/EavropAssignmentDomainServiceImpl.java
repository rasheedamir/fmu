package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropAssignmentDomainService;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropRejectedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;

import com.google.common.eventbus.AsyncEventBus;

@Service
@Validated
@Transactional
public class EavropAssignmentDomainServiceImpl implements EavropAssignmentDomainService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final VardgivarenhetRepository vardgivarenhetRepository;
    private final AsyncEventBus eventBus;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param eventBus
     */
	@Inject
	public EavropAssignmentDomainServiceImpl(EavropRepository eavropRepository, VardgivarenhetRepository vardgivarenhetRepository, AsyncEventBus eventBus) {
		this.eavropRepository = eavropRepository;
		this.vardgivarenhetRepository = vardgivarenhetRepository;
		this.eventBus = eventBus;
	}
	
	private AsyncEventBus getEventBus(){
		return this.eventBus;
	}
	
	

	@Override
	public void assignEavropToVardgivarenhet(EavropId eavropId, HsaId hsaIdVardgivarenhet) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavrop(eavropId);
		Vardgivarenhet vardgivarenhet = getVardgivarenhet(hsaIdVardgivarenhet);

		if(eavrop.getCurrentAssignedVardgivarenhet()!=null){
			throw new IllegalArgumentException(String.format("Eavrop %s already assigned to vardgivarenhet %s", eavropId.toString(), eavrop.getCurrentAssignedVardgivarenhet().getHsaId().toString() ));
		}
		eavrop.assignEavropToVardgivarenhet(vardgivarenhet);;
		log.debug(String.format("Eavrop %s assigned by :: %s", eavropId.toString(), hsaIdVardgivarenhet.toString()));
		
		handleEavropAssigned(eavropId, hsaIdVardgivarenhet);
	}

	@Override
	public void acceptEavropAssignment(EavropId eavropId, HsaId hsaIdVardgivarenhet) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavrop(eavropId);
		Vardgivarenhet vardgivarenhet = getVardgivarenhet(hsaIdVardgivarenhet);
		
		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", eavropId.toString(), hsaIdVardgivarenhet.toString() ));
		}
		eavrop.acceptEavropAssignment();
		log.debug(String.format("Eavrop %s accepted  by :: %s", eavropId.toString(), hsaIdVardgivarenhet.toString()));
		
		handleEavropAccepted(eavropId, hsaIdVardgivarenhet);
	}

	@Override
	public void rejectEavropAssignment(EavropId eavropId, HsaId hsaIdVardgivarenhet)  throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavrop(eavropId);
		Vardgivarenhet vardgivarenhet = getVardgivarenhet(hsaIdVardgivarenhet);
		
		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", eavropId.toString(), hsaIdVardgivarenhet.toString() ));
		}
		eavrop.rejectEavropAssignment();
		log.debug(String.format("Eavrop %s rejected  by :: %s", eavropId.toString(), hsaIdVardgivarenhet.toString()));
		
		handleEavropRejected(eavropId, hsaIdVardgivarenhet);
	}

	private Eavrop getEavrop(EavropId eavropId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}
	
	private Vardgivarenhet getVardgivarenhet(HsaId hsaIdVardgivarenhet) throws EntityNotFoundException{
		Vardgivarenhet vardgivarenhet = this.vardgivarenhetRepository.findByHsaId(hsaIdVardgivarenhet);
		if(vardgivarenhet==null){
			throw new EntityNotFoundException(String.format("Vardgivarenhet %s not found", hsaIdVardgivarenhet.toString()));
		}
		return vardgivarenhet;
	}

	private void handleEavropAssigned(EavropId eavropId, HsaId vardgivarenhetId){
		EavropAssignedToVardgivarenhetEvent event = new EavropAssignedToVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAssignedToVardgivarenhetEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}

	private void handleEavropAccepted(EavropId eavropId, HsaId vardgivarenhetId){
		EavropAcceptedByVardgivarenhetEvent event = new EavropAcceptedByVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropAcceptedByVardgivarenhetEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}

	private void handleEavropRejected(EavropId eavropId, HsaId vardgivarenhetId){
		EavropRejectedByVardgivarenhetEvent event = new EavropRejectedByVardgivarenhetEvent(eavropId, vardgivarenhetId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropRejectedByVardgivarenhetEvent created :: %s", event.toString()));
        }
		getEventBus().post(event);
	}
}
