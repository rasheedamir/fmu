package se.inera.fmu.domain.model.eavrop.document;


import java.util.UUID;

import org.apache.commons.lang.Validate;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.shared.DomainEvent;

public class DocumentRequestEvent extends EavropEvent {
	
	private final String documentId;
	//~ Constructors ===================================================================================================
    
	public DocumentRequestEvent(final ArendeId arendeId, final String documentId) {
		super(arendeId);
		this.documentId = documentId;
	}

	//~ Property Methods ===============================================================================================

	public String getDocumentId() {
		return documentId;
	}

	//add document id
	
//	create the document
//	document will have a type
	
	//~ Other Methods ==================================================================================================
}
