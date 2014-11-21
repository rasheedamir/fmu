package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropBaseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PagaendeEavropDTO;

public class PagaendeDTOMapper extends EavropBaseDTOMapper {

	public EavropBaseDTO map(Eavrop eavrop, PagaendeEavropDTO dto) {
		super.map(eavrop, dto);

		dto.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop());
		dto.setColor(eavrop.isEavropAcceptDaysDeviated() ? DANGER_COLOR : DEFAULT_COLOR);
		dto.setStartDate(eavrop.getStartDate() != null ? eavrop.getStartDate()
				.toDateTimeAtCurrentTime().getMillis() : null);
		dto.setNrOfDaysSinceStart(eavrop.getNumberOfDaysUsedDuringAssessment());

		return dto;
	}

	@Override
	public EavropBaseDTO map(Eavrop eavrop) {
		return map(eavrop, new PagaendeEavropDTO());
	}
}
