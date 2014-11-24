package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public interface Mapper {
	public EavropDTO map(Eavrop eavrop, EavropDTO dto);
	public EavropDTO map(Eavrop eavrop);
}
