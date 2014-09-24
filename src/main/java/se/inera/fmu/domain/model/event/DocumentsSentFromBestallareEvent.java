package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

@Entity
@ToString
public class DocumentsSentFromBestallareEvent extends EavropEvent{
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	DocumentsSentFromBestallareEvent() {
		// TODO Auto-generated constructor stub
	}
    
	public DocumentsSentFromBestallareEvent(final LocalDateTime eventDateTime) {
		super(eventDateTime);
   }

}
