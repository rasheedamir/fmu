package se.inera.fmu.interfaces.managing.dtomapper;

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
//		dto.setIsCompleted(eavrop.isin);
		dto.setTotalCompletionDays(eavrop.getNoOfDaysUsedForLastComplementRequest());
		dto.setDateIntygDelivered(eavrop.getIntygSentDateTime() != null ? eavrop.getIntygSentDateTime().getMillis(): null);
		dto.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null);
		dto.setAnsvarigUtredare(eavrop.getIntygSigningPerson() != null ?
				eavrop.getIntygSigningPerson().getName() :  null);
		dto.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null);
		dto.setIsCompensationApproved(compensation != null ? compensation.isApproved() : null);
		dto.setCompensationApprovalDate(compensation != null && compensation.getCompensationDateTime() != null ? 
				compensation.getCompensationDateTime().getMillis() : null);
		dto.setColor(toColorCode(eavrop));
		return dto;
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
