package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PagaendeEavropDTO;

public class PagaendeDTOMapper extends EavropDTOMapper {

	public EavropDTO map(Eavrop eavrop, PagaendeEavropDTO dto) {
		super.map(eavrop, dto);

		dto.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop());
		dto.setColor(eavrop.isEavropAssignmentAcceptDaysDeviated() ? DANGER_COLOR : DEFAULT_COLOR);
		dto.setStartDate(eavrop.getStartDate() != null ? eavrop.getStartDate()
				.toDateTimeAtCurrentTime().getMillis() : null);
		dto.setNrOfDaysSinceStart(eavrop.getNoOfAssesmentDays());
		dto.setUtredareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ?
				eavrop.getCurrentAssignedVardgivarenhet().getUnitName() : null);

		return dto;
	}

	@Override
	public EavropDTO map(Eavrop eavrop) {
		return map(eavrop, new PagaendeEavropDTO());
	}
}
