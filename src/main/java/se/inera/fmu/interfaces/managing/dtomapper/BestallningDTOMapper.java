package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.BestallningEavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropBaseDTO;



public class BestallningDTOMapper extends EavropBaseDTOMapper{
	
	public EavropBaseDTO map(Eavrop eavrop, BestallningEavropDTO dto) {
		super.map(eavrop, dto);
		dto.setPatientCity(eavrop.getInvanare() != null && eavrop.getInvanare().getHomeAddress() != null ?
				eavrop.getInvanare().getHomeAddress().getCity() : null);
		dto.setAntalDagarEfterForfragan(eavrop.getNumberOfAcceptanceDaysFromOrderDate());
		dto.setColor(eavrop.isEavropAcceptDaysDeviated() ? DANGER_COLOR : DEFAULT_COLOR);
		return dto;
	}
	
	@Override
	public EavropBaseDTO map(Eavrop eavrop) {
		return this.map(eavrop, new BestallningEavropDTO());
	}
}
