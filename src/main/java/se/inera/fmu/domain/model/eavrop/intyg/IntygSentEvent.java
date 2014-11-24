package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

public class IntygSentEvent extends EavropEvent{
	
	private final DateTime sentDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygSentEvent(final EavropId eavropId, final DateTime sentDateTime) {
		super(eavropId);
		Validate.notNull(sentDateTime);
		this.sentDateTime= sentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getSentDateTime() {
		return this.sentDateTime;
	}

	//~ Other Methods ==================================================================================================
}
