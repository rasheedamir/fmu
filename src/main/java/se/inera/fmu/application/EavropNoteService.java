package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;

/**
 * Service for handling Notes connected to the Eavrop 
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropNoteService {
	
	/**
	 * Add a note to the eavrop
	 * @param noteCommand
	 */
	public void addNote(AddNoteCommand noteCommand);
	
	/**
	 * Remove a note from the eavrop 
	 * @param noteCommand
	 */
	public void removeNote(RemoveNoteCommand noteCommand);

	
}
