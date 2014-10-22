package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class IntygComplemetsRequestedFromBestallareEvent extends EavropEvent{
	
	private final DateTime requestDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygComplemetsRequestedFromBestallareEvent(final ArendeId arendeId, final DateTime requestDateTime) {
		super(arendeId);
		Validate.notNull(requestDateTime);
		this.requestDateTime= requestDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getRequestDateTime() {
		return this.requestDateTime;
	}

	//~ Other Methods ==================================================================================================
}
