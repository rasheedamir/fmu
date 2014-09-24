package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
@Entity
@ToString
public class EavropAssignedToVardgivarenhetEvent extends EavropVardgivarenhetEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	EavropAssignedToVardgivarenhetEvent() {
	   //Needed by hibernate
    }
    
	public EavropAssignedToVardgivarenhetEvent(final LocalDateTime eventDateTime, final Vardgivarenhet vardgivarenhet ) {
		super(eventDateTime, vardgivarenhet);
   }

}
