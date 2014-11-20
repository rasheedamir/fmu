package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class DTOMapper {

	public EavropDTO map(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		if(eavrop == null)
			return dto;
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setEavropId(eavrop.getEavropId().getId())
		.setBestallareOrganisation(eavrop.getBestallaradministrator() != null ? 
				eavrop.getBestallaradministrator().getOrganisation() : null)
		.setLeverantorOrganisation(eavrop.getLandsting() != null ?
				eavrop.getLandsting().getName() : null)
		.setAntalDagarEfterForfragan(eavrop.getNumberOfAcceptanceDaysFromOrderDate())
		.setCreationTime(eavrop.getCreatedDate() != null ? eavrop.getCreatedDate().getMillis() : null)
		.setPatientCity(eavrop.getInvanare() != null && eavrop.getInvanare().getHomeAddress() != null ?
				eavrop.getInvanare().getHomeAddress().getCity() : null)
		.setUtredningType(eavrop.getUtredningType())
		.setStatus(eavrop.getStatus())
		.setBestallareEnhet(eavrop.getBestallaradministrator() != null ? 
				eavrop.getBestallaradministrator().getUnit() : null)
		.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop())
		.setRowColor(eavrop.isEavropAcceptDaysDeviated() ? "bg-danger": "fmu-table-color-inactive")
		
		.setStartDate(eavrop.getStartDate() != null ? 
				eavrop.getStartDate().toDateTimeAtCurrentTime().getMillis()
				: null)
				
		.setNrOfDaysAfterStart(eavrop.getNumberOfDaysUsedDuringAssessment())
		
		.setAntalDagarFromStartToAccepted(eavrop.getNoOfAssesmentDays())
		.setAntalDagarFromKompleteringBegarToBestallaren(eavrop.getNoOfDaysUsedForLastComplementRequest())
		.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getVardgivare().getName() : null)
		.setUtredareAnsvarigNamn(eavrop.getIntygSigningPerson() != null ?
				eavrop.getIntygSigningPerson().getName() :  null)
		.setIntygDeliveredDate(eavrop.getIntygSignedDateTime() != null ?
				eavrop.getIntygSignedDateTime().getMillis() : null)
		.setIsIntygComplete(eavrop.isApproved())
		.setEavropApprovedForPayment(eavrop.getEavropCompensationApproval() != null ?
				eavrop.getEavropCompensationApproval().isApproved() : null)
		.setEavropApprovalDatetime(eavrop.getEavropCompensationApproval() != null 
		&& eavrop.getEavropCompensationApproval().getCompensationDateTime() != null ?
				eavrop.getEavropCompensationApproval().getCompensationDateTime().getMillis() : null)
		.setAnsvarigUtredare(eavrop.getIntygSigningPerson() != null ? eavrop.getIntygSigningPerson().getName(): null)
		.setTotalCompletionDays(eavrop.getNoOfDaysUsedForLastComplementRequest());
		
		return dto;
	}
}
