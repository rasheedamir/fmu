package se.inera.fmu.application;

import se.inera.fmu.application.impl.AddNoteCommand;
import se.inera.fmu.application.impl.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.RemoveNoteCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropNoteDomainService {
	
	public void addNote(AddNoteCommand noteCommand);
	
	public void removeNote(RemoveNoteCommand noteCommand);

	
}
