package se.inera.fmu.domain.model.eavrop.document;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

/**
 * Domain event describing that documents regading the Eavrop have been sent by the customer/beställare 
 */
public class DocumentSentByBestallareEvent extends EavropEvent{
	
	private final ArendeId arendeId; 
	private final DateTime documentsSentDateTime;
	
	
	//~ Constructors ===================================================================================================
    
	public DocumentSentByBestallareEvent(final EavropId eavropId, final ArendeId arendeId, final DateTime documentsSentDateTime) {
		super(eavropId);
		this.arendeId = arendeId;
		Validate.notNull(documentsSentDateTime);
		this.documentsSentDateTime= documentsSentDateTime;
	}

	//~ Property Methods ===============================================================================================

	public ArendeId getArendeId() {
		return arendeId;
	}

	public DateTime getDocumentsSentDateTime() {
		return this.documentsSentDateTime;
	}

	
	//~ Other Methods ==================================================================================================
}
