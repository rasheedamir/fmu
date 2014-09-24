package se.inera.fmu.domain.model.event;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
@Entity
@ToString
public abstract class EavropPartyEvent extends EavropEvent {

	
	@OneToOne
	private Party party;
	
	
	public EavropPartyEvent() {
	}

	public EavropPartyEvent(final LocalDateTime eavropEventDateTime, final Party party) {
		super(eavropEventDateTime);
		Validate.notNull(party);
		setParty(party);
	}

	public Party getParty() {
		return party;
	}

	private void setParty(Party party) {
		this.party = party;
	}
	
	
	
}
