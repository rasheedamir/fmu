package se.inera.fmu.domain.model.event;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.party.Party;

public class IntygApprovedEvent extends EavropPartyEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	IntygApprovedEvent() {
        //Needed by hibernate
    }
    
	
	
	public IntygApprovedEvent(LocalDateTime eavropEventDateTime,
			Party party) {
		//TODO: fix constructor, and add properties
		//super(evarop, eavropEventDateTime, party);
	}
}
