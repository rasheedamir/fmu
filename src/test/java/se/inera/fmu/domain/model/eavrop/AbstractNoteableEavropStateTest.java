package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.note.Note;


public abstract class AbstractNoteableEavropStateTest extends AbstractEavropStateTest{
	
	@Test
	@Override
	public void testAddNoteToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		
		eavrop.addNote(createNote());
		
		assertNotNull(eavrop.getNotes());
		assertEquals(1, eavrop.getNotes().size()); 
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testRemoveNoteToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		Note note = createNote();
		eavrop.addNote(note);
		
		assertNotNull(eavrop.getNotes());
		assertEquals(1, eavrop.getNotes().size()); 
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());

		eavrop.removeNote(note);
		
		assertNotNull(eavrop.getNotes());
		assertEquals(0, eavrop.getNotes().size()); 
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());

	}

}
