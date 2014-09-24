package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.document.Document;

@Entity
@ToString
public class DocumentReceivedEvent extends DocumentEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	DocumentReceivedEvent() {
		// TODO Auto-generated constructor stub
	}
    
	public DocumentReceivedEvent(final LocalDateTime eventDateTime, final Document document) {
		super(eventDateTime, document);
   }

}
