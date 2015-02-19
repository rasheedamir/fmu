package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;

/**
 * Domain event describing when the eavrop is/has been started
 */
public class EavropStartEvent extends EavropEvent {
	
	private final ArendeId arendeid;
	private final DateTime eavropStartDateTime;
	
	//~ Constructors ===================================================================================================
    
	public EavropStartEvent(final EavropId eavropId, final ArendeId arendeid,  final DateTime eavropStartDate) {
		super(eavropId);
		this.arendeid = arendeid;
		this.eavropStartDateTime = eavropStartDate;
	}

	//~ Property Methods ===============================================================================================
	
	public ArendeId getArendeId() {
		return this.arendeid;
	}

	public DateTime getEavropStartDate() {
		return this.eavropStartDateTime;
	}

	//~ Other Methods ==================================================================================================
}
