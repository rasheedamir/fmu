package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoteDTO {
	private String noteId;
	private String contents;
	private String createdBy;
	private Long date;
	private boolean isRemovable;
}
