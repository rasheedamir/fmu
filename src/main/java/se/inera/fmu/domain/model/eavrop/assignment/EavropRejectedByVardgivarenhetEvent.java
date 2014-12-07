package se.inera.fmu.domain.model.eavrop.assignment;


import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

public class EavropRejectedByVardgivarenhetEvent extends EavropEvent{
	private final ArendeId arendeId;
	private final HsaId hsaId;
	
	//~ Constructors ===================================================================================================
	public EavropRejectedByVardgivarenhetEvent(final EavropId eavropId, final ArendeId arendeId, final HsaId vardgivarenhetHsaId) {
		super(eavropId);
		this.hsaId = vardgivarenhetHsaId;
		this.arendeId = arendeId;
	}

	//~ Property Methods ===============================================================================================

	public ArendeId getArendeId() {
		return arendeId;
	}
	
	public HsaId getHsaId() {
		return this.hsaId;
	}
}
