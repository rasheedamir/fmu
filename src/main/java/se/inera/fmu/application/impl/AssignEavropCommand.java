package se.inera.fmu.application.impl;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssignEavropCommand {
	private EavropId eavropId;
	private HsaId hsaId;
}
