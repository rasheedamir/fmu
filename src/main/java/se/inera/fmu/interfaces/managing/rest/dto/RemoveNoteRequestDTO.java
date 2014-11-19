package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveNoteRequestDTO {
	private String eavropId;
	private String noteId;
}
