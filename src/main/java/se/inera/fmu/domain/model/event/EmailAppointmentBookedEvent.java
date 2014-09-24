package se.inera.fmu.domain.model.event;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;

public class EmailAppointmentBookedEvent extends EavropEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event
	
	//~ Constructors ===================================================================================================

	EmailAppointmentBookedEvent() {
        //Needed by hibernate
    }
    
	public EmailAppointmentBookedEvent(final ArendeId arendeId, final LocalDateTime eventDateTime ) {
		//TODO: fix constructor, and add properties
		//super(arendeId, eventDateTime);
   }

}
