package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class IntygComplemetsRequestedFromBestallareEvent extends EavropEvent{
	
	private final LocalDateTime requestDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygComplemetsRequestedFromBestallareEvent(final ArendeId arendeId, final LocalDateTime requestDateTime) {
		super(arendeId);
		Validate.notNull(requestDateTime);
		this.requestDateTime= requestDateTime;
	}

	//~ Property Methods ===============================================================================================

	public LocalDateTime getRequestDateTime() {
		return this.requestDateTime;
	}

	//~ Other Methods ==================================================================================================
}
