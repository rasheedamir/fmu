package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.landsting.LandstingCode;


public class EavropCreatedEvent extends EavropEvent {
	
	private final ArendeId arendeid;
	private final LandstingCode landstingCode;
	
	//~ Constructors ===================================================================================================
    
	public EavropCreatedEvent(final EavropId eavropId, final ArendeId arendeid,  final LandstingCode landstingCode) {
		super(eavropId);
		this.arendeid = arendeid;
		this.landstingCode = landstingCode;
		
	}

	//~ Property Methods ===============================================================================================
	
	public ArendeId getArendeId() {
		return this.arendeid;
	}

	public LandstingCode getLandstingCode() {
		return this.landstingCode;
	}

	//~ Other Methods ==================================================================================================
}
