package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.BestallningEavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;



public class BestallningDTOMapper extends EavropDTOMapper{
	
	public EavropDTO map(Eavrop eavrop, BestallningEavropDTO dto) {
		super.map(eavrop, dto);
		dto.setPatientCity(eavrop.getInvanare() != null && eavrop.getInvanare().getHomeAddress() != null ?
				eavrop.getInvanare().getHomeAddress().getCity() : null);
		dto.setAntalDagarEfterForfragan(eavrop.getNumberOfAcceptanceDaysFromOrderDate());
		dto.setColor(eavrop.isEavropAssignmentAcceptDaysDeviated() ? DANGER_COLOR : DEFAULT_COLOR);
		return dto;
	}
	
	@Override
	public EavropDTO map(Eavrop eavrop) {
		return this.map(eavrop, new BestallningEavropDTO());
	}
}
