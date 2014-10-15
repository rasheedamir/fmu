package se.inera.fmu.domain.model.eavrop.document;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class DocumentSentByBestallareEvent extends EavropEvent{
	
	private final LocalDateTime documentsSentDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public DocumentSentByBestallareEvent(final ArendeId arendeId, final LocalDateTime documentsSentDateTime) {
		super(arendeId);
		Validate.notNull(documentsSentDateTime);
		this.documentsSentDateTime= documentsSentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public LocalDateTime getDocumentsSentDateTime() {
		return this.documentsSentDateTime;
	}

	//~ Other Methods ==================================================================================================
}
