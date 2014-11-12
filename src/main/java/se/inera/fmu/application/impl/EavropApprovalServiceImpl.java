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

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Transactional
public class EavropApprovalServiceImpl implements EavropApprovalService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropApprovalServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public void approveEavrop(ApproveEavropCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName())){
			person  = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		}
		Note note = null;
		if(!StringUtils.isBlankOrNull(aCommand.getComment())){
			note = new Note(NoteType.APPROVAL,aCommand.getComment(), person);
		}
		
		 EavropApproval eavropApproval = new EavropApproval(aCommand.getApproveDateTime(), person, note);
		
		 eavrop.approveEavrop(eavropApproval);

		if(log.isDebugEnabled()){
        	log.debug(String.format("EavropApproval added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleEavropApproval(eavrop.getEavropId(), aCommand.getApproveDateTime());
	}

	@Override
	public void approveEavropCompensation(ApproveEavropCompensationCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		
		Bestallaradministrator person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName())){
			person  = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		}
		Note note = null;
		if(!StringUtils.isBlankOrNull(aCommand.getComment())){
			note = new Note(NoteType.APPROVAL,aCommand.getComment(), person);
		}
		
		 EavropCompensationApproval eavropCompensationApproval = new EavropCompensationApproval(aCommand.isApproved(), aCommand.getApproveDateTime(), person, note);
		
		 eavrop.approveEavropCompensation(eavropCompensationApproval);

		if(log.isDebugEnabled()){
        	log.debug(String.format("EavropCompensationApproval added for eavrop:: %s", eavrop.getEavropId().toString()));
        }
		
		handleEavropCompensationApproval(eavrop.getEavropId(), aCommand.getApproveDateTime());
		
	}

	private void validateApproveEavropCommand(ApproveEavropCommand command){
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getApproveDateTime());
	}

	private void validateApproveEavropCompensationCommand(ApproveEavropCompensationCommand command){
		Validate.notNull(command.getArendeId());
		Validate.notNull(command.getApproveDateTime());
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
	
	private void handleEavropApproval(EavropId eavropId, DateTime approvedDateTime){
	//TODO: Should we have events when eavrop is approved?	
//		 IntygApprovedByBestallareEvent event = new IntygApprovedByBestallareEvent(eavropId, approvedDateTime);
//        if(log.isDebugEnabled()){
//        	log.debug(String.format("IntygApprovedByBestallareEvent created :: %s", event.toString()));
//        }
//		getDomainEventPublisher().post(event);
	}

	private void handleEavropCompensationApproval(EavropId eavropId, DateTime complementRequestDateTime){
		//TODO: Should we have events when eavrop is approved?	
//		 IntygApprovedByBestallareEvent event = new IntygApprovedByBestallareEvent(eavropId, approvedDateTime);
//       if(log.isDebugEnabled()){
//       	log.debug(String.format("IntygApprovedByBestallareEvent created :: %s", event.toString()));
//       }
//		getDomainEventPublisher().post(event);
	}
}
