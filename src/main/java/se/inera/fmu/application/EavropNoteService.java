package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropNoteService {
	
	public void addNote(AddNoteCommand noteCommand);
	
	public void removeNote(RemoveNoteCommand noteCommand);

	
}
