package se.inera.fmu.domain.model.eavrop.document;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class DocumentSentByBestallareEvent extends EavropEvent{
	
	private final DateTime documentsSentDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public DocumentSentByBestallareEvent(final Long eavropId, final DateTime documentsSentDateTime) {
		super(eavropId);
		Validate.notNull(documentsSentDateTime);
		this.documentsSentDateTime= documentsSentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getDocumentsSentDateTime() {
		return this.documentsSentDateTime;
	}

	//~ Other Methods ==================================================================================================
}
