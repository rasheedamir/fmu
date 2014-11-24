package se.inera.fmu.interfaces.managing.dtomapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApproval;
import se.inera.fmu.interfaces.managing.rest.dto.CompletedEavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class CompletedEavropDTOMapper extends EavropDTOMapper {

	public EavropDTO map(Eavrop eavrop, CompletedEavropDTO dto) {
		super.map(eavrop, dto);
		EavropCompensationApproval compensation = eavrop.getEavropCompensationApproval();
		dto.setDagarFromStartToAccepted(eavrop.getNoOfAcceptDays());
		dto.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop());
		dto.setIsCompleted(eavrop.isApproved());
		dto.setTotalCompletionDays(eavrop.getNoOfDaysUsedForLastComplementRequest());
		dto.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null);
		dto.setAnsvarigUtredare(eavrop.getIntygSigningPerson() != null ?
				eavrop.getIntygSigningPerson().getName() :  null);
		dto.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null);
		dto.setCompensationApprovedStatusAndDate(compensation == null ? 
				toApprovedyyyMMDD(compensation)  : null);
		dto.setColor(toColorCode(eavrop));
		return dto;
	}
	
	private String toApprovedyyyMMDD(EavropCompensationApproval compensation) {
		String retval = null;
		if(compensation == null)
			return retval;
		
		retval = compensation.isApproved() ? "Ja" : "Nej";
		
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
		String date = compensation.getCompensationDateTime() != null ? 
				dtfOut.print(compensation.getCompensationDateTime()) : "";
		return retval + " " + date;
	}

	@Override
	public EavropDTO map(Eavrop eavrop) {
		return map(eavrop, new CompletedEavropDTO());
	}
	
	private String toColorCode(Eavrop eavrop) {
		if(eavrop.getStatus() == null || !eavrop.getStatus().isCompleted())
			return DEFAULT_COLOR;
		switch (eavrop.getStatus()) {
		case SENT:
			return eavrop.isDeviated() ? DANGER_COLOR : DEFAULT_COLOR;
		case APPROVED:
			return WARNING_COLOR;
		case CLOSED:
			return eavrop.getEavropCompensationApproval() != null && eavrop.getEavropCompensationApproval().isApproved() ?
					SUCCESS_COLOR: DANGER_COLOR;
		default:
			return DEFAULT_COLOR;
		}
	}
	
}
