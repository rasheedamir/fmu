package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
import lombok.ToString;

@Entity
@DiscriminatorValue("APPROVED")
@ToString
public class IntygComplementRequestInformation extends IntygInformation{

	public IntygComplementRequestInformation(
			LocalDateTime informationTimestamp, Party party) {
		super(informationTimestamp, party);
	}

}
