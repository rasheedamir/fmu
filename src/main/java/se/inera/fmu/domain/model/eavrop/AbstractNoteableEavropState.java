package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.note.Note;

/**
* Many of the states allows for notes to be added or removed so an abstract class provides that behavior 
* for multiple sub classes  
*/
public abstract class AbstractNoteableEavropState extends AbstractEavropState {

	private static final long serialVersionUID = 1L;

	@Override
	public void addNote(Eavrop eavrop, Note note) {
		eavrop.doAddNote(note);
		
	}
	
	@Override
	public void removeNote(Eavrop eavrop, Note note){
		eavrop.doRemoveNote(note);
	}

}
