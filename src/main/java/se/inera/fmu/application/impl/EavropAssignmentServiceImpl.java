package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Slf4j
public class EavropAssignmentServiceImpl implements EavropAssignmentService {

    private final EavropRepository eavropRepository;
    private final VardgivarenhetRepository vardgivarenhetRepository;

    /**
     * Constructor
     * @param eavropRepository
     */
	@Inject
	public EavropAssignmentServiceImpl(EavropRepository eavropRepository, VardgivarenhetRepository vardgivarenhetRepository) {
		this.eavropRepository = eavropRepository;
		this.vardgivarenhetRepository = vardgivarenhetRepository;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void assignEavropToVardgivarenhet(AssignEavropCommand aCommand) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getVardgivarenhetHsaId());

		if(eavrop.getCurrentAssignedVardgivarenhet()!=null){
			throw new IllegalArgumentException(String.format("Eavrop %s already assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), eavrop.getCurrentAssignedVardgivarenhet().getHsaId().toString() ));
		}

		HoSPerson assigningPerson = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName()) || aCommand.getPersonHsaId() != null){
			assigningPerson  = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		}
		eavrop.assignEavropToVardgivarenhet(vardgivarenhet, assigningPerson);
		log.debug(String.format("Eavrop %s assigned to :: %s", aCommand.getEavropId().toString(), aCommand.getVardgivarenhetHsaId().toString()));
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void acceptEavropAssignment(AcceptEavropAssignmentCommand aCommand) throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getVardgivarenhetHsaId());

		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), aCommand.getVardgivarenhetHsaId().toString() ));
		}
		HoSPerson acceptingPerson = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName()) || aCommand.getPersonHsaId() != null){
			acceptingPerson  = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		}
		
		eavrop.acceptEavropAssignment(acceptingPerson);
		log.debug(String.format("Eavrop %s accepted  by :: %s", aCommand.getEavropId().toString(), aCommand.getVardgivarenhetHsaId().toString()));
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void rejectEavropAssignment(RejectEavropAssignmentCommand aCommand)  throws EntityNotFoundException, IllegalArgumentException{
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		Vardgivarenhet vardgivarenhet = getVardgivarenhetByHsaId(aCommand.getVardgivarenhetHsaId());

		if(!vardgivarenhet.equals(eavrop.getCurrentAssignedVardgivarenhet())){
			throw new IllegalArgumentException(String.format("Eavrop %s not assigned to vardgivarenhet %s", aCommand.getEavropId().toString(), aCommand.getVardgivarenhetHsaId().toString()));
		}
		
		HoSPerson rejectingPerson = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName()) || aCommand.getPersonHsaId() != null){
			rejectingPerson = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(), aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		}

		eavrop.rejectEavropAssignment(rejectingPerson, null);
		log.debug(String.format("Eavrop %s rejected  by :: %s", aCommand.getEavropId().toString(), aCommand.getVardgivarenhetHsaId().toString()));
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
}
