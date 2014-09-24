package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

@Entity
@ToString
public class EavropCreatedEvent extends EavropEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	EavropCreatedEvent() {
        //Needed by hibernate
    }
    
	public EavropCreatedEvent(final LocalDateTime eventDateTime ) {
		super(eventDateTime);
   }

}
