package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;

@Getter
public class AddNoteRequestDTO {
	private String eavropId;
	private String text;
    
    public AddNoteRequestDTO setEavropId(String eavropId) {
		this.eavropId = eavropId;
		return this;
	}
    
    public AddNoteRequestDTO setText(String text) {
		this.text = text;
		return this;
	}
}
