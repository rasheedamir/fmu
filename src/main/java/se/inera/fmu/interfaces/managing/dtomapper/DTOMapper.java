package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class DTOMapper {

	public EavropDTO mapToOverviewDTO(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setEavropId(eavrop.getEavropId().getId())
		.setBestallareOrganisation(eavrop.getBestallaradministrator().getOrganisation())
		.setLeverantorOrganisation(eavrop.getLandsting().getName())
		.setAntalDagarEfterForfragan(eavrop.getNumberOfAcceptanceDaysFromOrderDate())
		.setCreationTime(eavrop.getCreatedDate().getMillis())
		.setPatientCity(eavrop.getInvanare().getHomeAddress().getCity())
		.setUtredningType(eavrop.getUtredningType())
		.setStatus(eavrop.getStatus())
		.setBestallareEnhet(eavrop.getBestallaradministrator().getUnit())
		.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop())
		.setRowColor(eavrop.isEavropAcceptDaysDeviated() ? "bg-danger": null)
		
		.setStartDate(eavrop.getStartDate())
		.setNrOfDaysAfterStart(eavrop.getNumberOfDaysUsedDuringAssessment())
		
		;
		
		return dto;
	}
}
