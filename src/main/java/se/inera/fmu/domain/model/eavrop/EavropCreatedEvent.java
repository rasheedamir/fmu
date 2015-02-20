package se.inera.fmu.domain.model.eavrop;

/**
 * Domain event describing that the eavrop has been created
 */
public class EavropCreatedEvent extends EavropEvent {
	
	private final ArendeId arendeId;

	public EavropCreatedEvent(final EavropId eavropId, final ArendeId arendeid) {
		super(eavropId);
		this.arendeId = arendeid;
	}

	//~ Property Methods ===============================================================================================
	public ArendeId getArendeId() {
		return this.arendeId;
	}
}
