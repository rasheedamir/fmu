package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
import lombok.ToString;

@Entity
@DiscriminatorValue("COMPLEMENT")
@ToString
public class IntygApprovedInformation extends IntygInformation{

	public IntygApprovedInformation(LocalDateTime informationTimestamp, Party party) {
		super(informationTimestamp, party);
	}

}
