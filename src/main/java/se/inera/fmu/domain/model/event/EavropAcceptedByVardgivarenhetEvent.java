package se.inera.fmu.domain.model.event;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;

public class EavropAcceptedByVardgivarenhetEvent extends EavropVardgivarenhetEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	EavropAcceptedByVardgivarenhetEvent() {
	   //Needed by hibernate
    }

    
	public EavropAcceptedByVardgivarenhetEvent(final LocalDateTime eventDateTime, final Vardgivarenhet vardgivarenhet ) {
		super(eventDateTime, vardgivarenhet);
   }

}
