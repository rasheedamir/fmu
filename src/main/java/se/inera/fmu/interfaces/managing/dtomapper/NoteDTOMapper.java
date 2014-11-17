package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.interfaces.managing.rest.dto.NoteDTO;

public class NoteDTOMapper {
	public NoteDTO map(Note note){
		NoteDTO dto = new NoteDTO();
		dto.setContents(note.getText());
		dto.setDate(note.getCreatedDate().getMillis());
		dto.setCreatedBy(String.format("%s, %s, %s", note.getPerson().getName(), note.getPerson().getRole(), note.getPerson().getOrganisation()));
		return dto;
	}
}
