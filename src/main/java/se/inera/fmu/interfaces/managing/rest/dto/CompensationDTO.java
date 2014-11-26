package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.List;

import se.inera.fmu.domain.model.eavrop.EavropDeviationEventDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompensationDTO {
	private String arendeId;

	private String utredningType;
	
	private String utforareOrganisation;
	
	private String utforareNamn;
	
	private Boolean tolkBooked;

	private Integer utredningDuration;
	
	private Integer nrDaysAfterCompletetion;
	
	private Integer nrAvikelser;
	
	private Integer nrUtredningstarts;
	
	private Boolean isCompletedAndApproved;
	
	// Specifikation
	private List<EavropDeviationEventDTO> avikelser;

	// TillaggTjanster
	private List<AdditionalServicesDTO> tillaggTjanster;
	
}
