package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;
import se.inera.fmu.domain.model.eavrop.EavropStateType;

@Setter
@Getter
public class EavropDTO {
	private String arendeId;

	private String eavropId;

	private String utredningType;
	
	private Long creationTime;
	
	private EavropStateType status;

	private String bestallareEnhet;

	private String bestallareOrganisation;

	private String mottagarenOrganisation;
}
