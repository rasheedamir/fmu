package se.inera.fmu.domain.model.eavrop;

import java.util.HashSet;

import se.inera.fmu.domain.model.eavrop.note.Note;

/**
* Many of the states allows for notes to be added so an abstract class provides that behavior 
* for multiple sub classes  
*/
public abstract class AbstractNoteableEavropState extends AbstractEavropState {
	@Override
	public void addNote(Eavrop eavrop, Note note) {
		eavrop.addToNotes(note);
		
	}

}
