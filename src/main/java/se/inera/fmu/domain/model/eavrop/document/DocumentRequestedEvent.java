package se.inera.fmu.domain.model.eavrop.document;



import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

public class DocumentRequestedEvent extends EavropEvent {
	
	private final String documentId;
	//~ Constructors ===================================================================================================
    
	public DocumentRequestedEvent(final EavropId eavropId, final String documentId) {
		super(eavropId);
		this.documentId = documentId;
	}

	//~ Property Methods ===============================================================================================

	public String getDocumentId() {
		return documentId;
	}
	
	//~ Other Methods ==================================================================================================
}
