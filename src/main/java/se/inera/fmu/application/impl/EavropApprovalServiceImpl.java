package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropApprovalService;
import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropApproval;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApproval;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.Bestallarsamordnare;

/**
 * 
 * @see EavropApprovalService
 *
 */
@Service
@Validated
@Slf4j
public class EavropApprovalServiceImpl implements EavropApprovalService {

    private final EavropRepository eavropRepository;
    

    /**
     * Constructor
     * @param eavropRepository
     */
	@Inject
	public EavropApprovalServiceImpl(EavropRepository eavropRepository) {
		this.eavropRepository = eavropRepository;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId approveEavrop(ApproveEavropCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		Note note = null;
		if(!StringUtils.isBlankOrNull(aCommand.getComment())){
			note = new Note(NoteType.APPROVAL,aCommand.getComment(), person);
		}
		
		 EavropApproval eavropApproval = new EavropApproval(aCommand.getApproveDateTime(), person, note);
		 eavrop.approveEavrop(eavropApproval);

		if(log.isDebugEnabled()){
        	log.debug(String.format("EavropApproval added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		return eavrop.getEavropId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public EavropId approveEavropCompensation(ApproveEavropCompensationCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallarsamordnare person = aCommand.getBestallarsamordnare();
		Note note = null;
		if(!StringUtils.isBlankOrNull(aCommand.getComment())){
			note = new Note(NoteType.COMPENSATION_APPROVAL,aCommand.getComment(), person);
		}
		
		 EavropCompensationApproval eavropCompensationApproval = new EavropCompensationApproval(aCommand.getApproved(), aCommand.getApproveDateTime(), person, note);
		 eavrop.approveEavropCompensation(eavropCompensationApproval);

		if(log.isDebugEnabled()){
        	log.debug(String.format("EavropCompensationApproval added for eavrop:: %s", eavrop.getEavropId().toString()));
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
