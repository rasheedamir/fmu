package se.inera.fmu.domain.model.eavrop.intyg;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

public class IntygSentEvent extends EavropEvent{
	
	private final ArendeId arendeId;
	private final DateTime sentDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public IntygSentEvent(final EavropId eavropId, final ArendeId arendeId, final DateTime sentDateTime) {
		super(eavropId);
		Validate.notNull(arendeId);
		Validate.notNull(sentDateTime);
		this.arendeId = arendeId;
		this.sentDateTime= sentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public ArendeId getArendeId() {
		return arendeId;
	}
	
	public DateTime getSentDateTime() {
		return this.sentDateTime;
	}


	//~ Other Methods ==================================================================================================
}
