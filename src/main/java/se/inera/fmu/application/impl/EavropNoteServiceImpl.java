package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.EavropNoteService;
import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * 
 * @see EavropNoteService
 *
 */
@Service
@Validated
@Slf4j
public class EavropNoteServiceImpl implements EavropNoteService {

    private final EavropRepository eavropRepository;

    /**
     * Constructor
     * @param eavropRepository
     */
	@Inject
	public EavropNoteServiceImpl(EavropRepository eavropRepository) {
		this.eavropRepository = eavropRepository;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addNote(AddNoteCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		HoSPerson person = null;
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName()) || aCommand.getPersonHsaId()!=null){
			person  = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		}
		
		Note note = new Note(NoteType.EAVROP, aCommand.getText(), person);
		eavrop.addNote(note);
		
		if(log.isDebugEnabled()){
        	log.debug(String.format("Note %s added to eavrop:: %s", note.getNoteId(), eavrop.getEavropId().toString()));
        }

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeNote(RemoveNoteCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		
		Note note = eavrop.getNote(aCommand.getNoteId());
		if(note==null){
			throw new EntityNotFoundException(String.format("Note with NoteId: %s not found on eavrop with eavropId: %s", aCommand.getNoteId().toString(), aCommand.getEavropId()));
		}
		
		if(note.isRemovableBy(aCommand.getPersonHsaId())){
			eavrop.removeNote(note);
			if(log.isDebugEnabled()){
	        	log.debug(String.format("Note %s removed from eavrop:: %s", note.getNoteId(), eavrop.getEavropId().toString()));
	        }
		}else{
			throw new IllegalArgumentException(String.format("Note with NoteId: %s not eligeble for removal by person with HsaId: %s", aCommand.getNoteId().toString(), aCommand.getPersonHsaId()));
		}
	}

	private Eavrop getEavropByEavropId(EavropId eavropId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}
}
