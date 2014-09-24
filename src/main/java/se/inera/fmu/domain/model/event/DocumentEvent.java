package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.document.Document;

@Entity
@ToString
public abstract class DocumentEvent extends EavropEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event
	@OneToOne 
    @JoinColumn(name="DOCUMENT_ID") 
	private Document document;
	//~ Constructors ===================================================================================================

	DocumentEvent() {
		// TODO Auto-generated constructor stub
	}
    
	DocumentEvent(final LocalDateTime eventDateTime, final Document document ) {
		super(eventDateTime);
		this.setDocument(document);
   }

	public Document getDocument() {
		return document;
	}

	private void setDocument(Document document) {
		this.document = document;
	}

	
	
}
