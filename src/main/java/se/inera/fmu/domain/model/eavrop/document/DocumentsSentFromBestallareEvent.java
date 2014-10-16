package se.inera.fmu.domain.model.eavrop.document;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class DocumentsSentFromBestallareEvent extends EavropEvent{
	
	private final DateTime documentsSentDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public DocumentsSentFromBestallareEvent(final ArendeId arendeId, final DateTime documentsSentDateTime) {
		super(arendeId);
		Validate.notNull(documentsSentDateTime);
		this.documentsSentDateTime= documentsSentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getDocumentsSentDateTime() {
		return this.documentsSentDateTime;
	}

	//~ Other Methods ==================================================================================================
}
