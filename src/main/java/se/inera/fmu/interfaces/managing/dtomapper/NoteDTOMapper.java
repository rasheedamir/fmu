package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.interfaces.managing.rest.dto.NoteDTO;

public class NoteDTOMapper {
	public NoteDTO map(Note note, User currentUser){
		NoteDTO dto = new NoteDTO();
		dto.setNoteId(note.getNoteId().getNoteId());
		dto.setContents(note.getText());
		dto.setDate(note.getCreatedDate().getMillis());
		dto.setCreatedBy(String.format("%s, %s, %s", note.getPerson().getName(), getRoleName(note.getPerson().getRole()), note.getPerson().getOrganisation()));
		dto.setRemovable(note.isRemovableBy(new HsaId(currentUser.getHsaId())));
		return dto;
	}

	private String getRoleName(String role) {
		switch (role) {
		case "ROLE_SAMORDNARE":
			return "Samordnare";
		case "ROLE_UTREDARE":
			return "Utredare";
		default:
			break;
		}
		
		return null;
	}
}
