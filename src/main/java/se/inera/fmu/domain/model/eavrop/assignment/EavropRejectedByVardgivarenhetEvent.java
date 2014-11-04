package se.inera.fmu.domain.model.eavrop.assignment;


import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

public class EavropRejectedByVardgivarenhetEvent extends EavropEvent{
	private final HsaId hsaId;
	
	//~ Constructors ===================================================================================================
	public EavropRejectedByVardgivarenhetEvent(final Long eavropId, final HsaId vardgivarenhetHsaId) {
		super(eavropId);
		this.hsaId = vardgivarenhetHsaId;
	}

	//~ Property Methods ===============================================================================================

	public HsaId getHsaId() {
		return this.hsaId;
	}
}
