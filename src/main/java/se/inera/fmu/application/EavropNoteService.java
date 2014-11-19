package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropNoteService {
	
	public void addNote(AddNoteCommand noteCommand);
	
	public void removeNote(RemoveNoteCommand noteCommand);

	
}
