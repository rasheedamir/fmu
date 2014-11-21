package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropBaseDTO;

public interface Mapper {
	public EavropBaseDTO map(Eavrop eavrop, EavropBaseDTO dto);
	public EavropBaseDTO map(Eavrop eavrop);
}
