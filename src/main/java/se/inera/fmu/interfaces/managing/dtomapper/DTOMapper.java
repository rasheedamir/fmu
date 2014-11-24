//package se.inera.fmu.interfaces.managing.dtomapper;
//
//import se.inera.fmu.domain.model.eavrop.Eavrop;
//import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
//
//public class DTOMapper {
//
//	private static final String DEFAULT_COLOR = "fmu-table-color-inactive";
//	private static final String DANGER_COLOR = "bg-danger";
//	private static final String WARNING_COLOR = "bg-warning";
//	private static final String SUCCESS_COLOR = "bg-success";
//
//	public EavropDTO map(Eavrop eavrop) {
//		EavropDTO dto = new EavropDTO();
//		if(eavrop == null)
//			return dto;
//		
//		dto.setArendeId(eavrop.getArendeId().toString())
//		.setEavropId(eavrop.getEavropId().getId())
//		.setBestallareOrganisation(eavrop.getBestallaradministrator() != null ? 
//				eavrop.getBestallaradministrator().getOrganisation() : null)
//		.setLeverantorOrganisation(eavrop.getLandsting() != null ?
//				eavrop.getLandsting().getName() : null)
//		.setAntalDagarEfterForfragan(eavrop.getNumberOfAcceptanceDaysFromOrderDate())
//		.setCreationTime(eavrop.getCreatedDate() != null ? eavrop.getCreatedDate().getMillis() : null)
//		.setPatientCity(eavrop.getInvanare() != null && eavrop.getInvanare().getHomeAddress() != null ?
//				eavrop.getInvanare().getHomeAddress().getCity() : null)
//		.setUtredningType(eavrop.getUtredningType())
//		.setStatus(eavrop.getStatus())
//		.setBestallareEnhet(eavrop.getBestallaradministrator() != null ? 
//				eavrop.getBestallaradministrator().getUnit() : null)
//		.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop())
//		.setBestallningRowColor(eavrop.isEavropAcceptDaysDeviated() ? "bg-danger": DEFAULT_COLOR)
//		
//		.setStartDate(eavrop.getStartDate() != null ? 
//				eavrop.getStartDate().toDateTimeAtCurrentTime().getMillis()
//				: null)
//				
//		.setNrOfDaysAfterStart(eavrop.getNumberOfDaysUsedDuringAssessment())
//		
//		.setAntalDagarFromStartToAccepted(eavrop.getNoOfAcceptDays())
//		.setNrOfDaysAfterStart(eavrop.getNoOfAssesmentDays())
//		.setAntalDagarFromKompleteringBegarToBestallaren(eavrop.getNoOfDaysUsedForLastComplementRequest())
//		.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
//				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null)
//		.setUtredareAnsvarigNamn(eavrop.getIntygSigningPerson() != null ?
//				eavrop.getIntygSigningPerson().getName() :  null)
//		.setIntygDeliveredDate(eavrop.getIntygSentDateTime() != null ?
//				eavrop.getIntygSentDateTime().getMillis() : null)
//		.setIsIntygComplete(eavrop.isApproved())
//		.setEavropApprovedForPayment(eavrop.getEavropCompensationApproval() != null ?
//				eavrop.getEavropCompensationApproval().isApproved() : null)
//		.setEavropApprovalDatetime(eavrop.getEavropCompensationApproval() != null 
//		&& eavrop.getEavropCompensationApproval().getCompensationDateTime() != null ?
//				eavrop.getEavropCompensationApproval().getCompensationDateTime().getMillis() : null)
//		.setAnsvarigUtredare(eavrop.getIntygSigningPerson() != null ? eavrop.getIntygSigningPerson().getName(): null)
//		.setTotalCompletionDays(eavrop.getNoOfDaysUsedForLastComplementRequest())
//		.setEavropCompletionStatus(toColorCode(eavrop));
//		
//		return dto;
//	}
//
//	private String toColorCode(Eavrop eavrop) {
//		if(eavrop.getStatus() == null || !eavrop.getStatus().isCompleted())
//			return DEFAULT_COLOR;
//		switch (eavrop.getStatus()) {
//		case SENT:
//			return eavrop.isDeviated() ? DANGER_COLOR : DEFAULT_COLOR;
//		case APPROVED:
//			return WARNING_COLOR;
//		case CLOSED:
//			return eavrop.getEavropCompensationApproval() != null && eavrop.getEavropCompensationApproval().isApproved() ?
//					SUCCESS_COLOR: DANGER_COLOR;
//		default:
//			return DEFAULT_COLOR;
//		}
//	}
//}

// TODO remove this file when done refactoring
