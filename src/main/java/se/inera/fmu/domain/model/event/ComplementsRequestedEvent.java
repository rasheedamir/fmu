package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.party.Party;

@Entity
@ToString
public class ComplementsRequestedEvent extends EavropPartyEvent {
	//TODO: Implement properties concerning this event and listeners listening to this event

	//~ Constructors ===================================================================================================

	ComplementsRequestedEvent() {
        //Needed by hibernate
    }
    
	public ComplementsRequestedEvent(final LocalDateTime eventDateTime, final Party requestedByParty ) {
		super(eventDateTime, requestedByParty);
   }

}
