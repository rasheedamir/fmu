package se.inera.fmu.domain.model.eavrop.assignment;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Domain event describing that the eavrop assignment has been accepted by the care giving unit/vardgivarenhet
 */
public class EavropAcceptedByVardgivarenhetEvent extends EavropEvent{
	private final ArendeId arendeId;
	private final HsaId hsaId;
	
	//~ Constructors ===================================================================================================
	public EavropAcceptedByVardgivarenhetEvent(final EavropId eavropId, final ArendeId arendeId, final HsaId vardgivarenhetHsaId) {
		super(eavropId);
		this.hsaId = vardgivarenhetHsaId;
		this.arendeId = arendeId;
	}

	//~ Property Methods ===============================================================================================

	public HsaId getHsaId() {
		return this.hsaId;
	}
	
	public ArendeId getArendeId() {
		return this.arendeId;
	}
}
