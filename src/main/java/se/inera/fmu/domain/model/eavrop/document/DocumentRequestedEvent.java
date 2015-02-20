package se.inera.fmu.domain.model.eavrop.document;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;

/**
 * Domain event describing that a document have been requested
 */
public class DocumentRequestedEvent extends EavropEvent {
	
	private final String documentId;
	private final ArendeId arendeId; 
	private final String documentName;
	private final DateTime documentDateTime;
	private final Person requestPerson;
	private final Note requestNote;
	
	//~ Constructors ===================================================================================================
    
	public DocumentRequestedEvent(final EavropId eavropId, final ArendeId arendeId, final String documentId, final String documentName, final DateTime documentDateTime, final Person person, final Note requestNote){
		super(eavropId);
		this.documentId = documentId;
		this.arendeId = arendeId;
		this.documentName = documentName;
		this.documentDateTime = documentDateTime;
		this.requestPerson = person;
		this.requestNote = requestNote;
	}

	//~ Property Methods ===============================================================================================

	public String getDocumentId() {
		return documentId;
	}

	public ArendeId getArendeId() {
		return arendeId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public DateTime getDocumentDateTime() {
		return documentDateTime;
	}

	public Person getRequestPerson() {
		return requestPerson;
	}

	public Note getRequestNote() {
		return requestNote;
	}
	
	//~ Other Methods ==================================================================================================
}
