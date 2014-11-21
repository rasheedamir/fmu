package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.CompletedEavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropBaseDTO;

public class CompletedEavropDTOMapper extends EavropBaseDTOMapper {

	public EavropBaseDTO map(Eavrop eavrop, CompletedEavropDTO dto) {
		super.map(eavrop, dto);
		
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
//		dto.setCompensationApprovedStatusAndDate(compensationApprovedStatusAndDate);
		return dto;
	}
	
	@Override
	public EavropBaseDTO map(Eavrop eavrop) {
		return super.map(eavrop, new CompletedEavropDTO());
	}
	
}
