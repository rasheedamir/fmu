package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class IntygSignedEvent extends EavropEvent{
	
	private final DateTime signedDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygSignedEvent(final ArendeId arendeId, final DateTime signedDateTime) {
		super(arendeId);
		Validate.notNull(signedDateTime);
		this.signedDateTime= signedDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getSignedDateTime() {
		return this.signedDateTime;
	}

	//~ Other Methods ==================================================================================================
}
