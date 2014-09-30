package se.inera.fmu.domain.model.eavrop.assignment;


import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

public class EavropAssignedToVardgivarenhetEvent extends EavropEvent{
	private final HsaId hsaId;
	
	//~ Constructors ===================================================================================================
	public EavropAssignedToVardgivarenhetEvent(final ArendeId arendeId, final HsaId vardgivarenhetHsaId) {
		super(arendeId);
		this.hsaId = vardgivarenhetHsaId;
	}

	//~ Property Methods ===============================================================================================

	public HsaId getHsaId() {
		return this.hsaId;
	}
}
