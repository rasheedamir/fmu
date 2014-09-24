package se.inera.fmu.domain.model.event;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;

public class IntygSignedEvent extends EavropEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	IntygSignedEvent() {
        //Needed by hibernate
    }
    
	public IntygSignedEvent(LocalDateTime eavropEventDateTime) {
		//TODO: fix constructor, and add properties
		//super(evarop, eavropEventDateTime);
	}

	

}
