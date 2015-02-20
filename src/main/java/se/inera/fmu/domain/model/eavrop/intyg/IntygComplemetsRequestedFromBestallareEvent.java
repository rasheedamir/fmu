package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

/**
 * Domain event describing that the beställare/customer has requested additional complements to the intyg related to the eavrop
 */
public class IntygComplemetsRequestedFromBestallareEvent extends EavropEvent{
	
	private final DateTime requestDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygComplemetsRequestedFromBestallareEvent(final EavropId eavropId, final DateTime requestDateTime) {
		super(eavropId);
		Validate.notNull(requestDateTime);
		this.requestDateTime= requestDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getRequestDateTime() {
		return this.requestDateTime;
	}

	//~ Other Methods ==================================================================================================
}
