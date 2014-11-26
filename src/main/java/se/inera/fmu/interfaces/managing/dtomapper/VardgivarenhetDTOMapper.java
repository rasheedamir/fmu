package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.interfaces.managing.rest.dto.VardgivarenhetDTO;

public class VardgivarenhetDTOMapper {
	public VardgivarenhetDTO map(Vardgivarenhet ve){
		VardgivarenhetDTO dto = new VardgivarenhetDTO();
		dto.setId(ve.getId());
		dto.setHsaId(ve.getHsaId().getHsaId());
		dto.setUnitName(ve.getUnitName());
		return dto;
	}
}
