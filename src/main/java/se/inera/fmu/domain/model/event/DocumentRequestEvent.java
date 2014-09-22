package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.document.Document;
import se.inera.fmu.domain.model.eavrop.ArendeId;
@Entity
@ToString
public class DocumentRequestEvent extends DocumentEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	DocumentRequestEvent() {
		// TODO Auto-generated constructor stub
	}
    
	public DocumentRequestEvent(final LocalDateTime eventDateTime, final Document document) {
		super(eventDateTime, document);
   }

}
