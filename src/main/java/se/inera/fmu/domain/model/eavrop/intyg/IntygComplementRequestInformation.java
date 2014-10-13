package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("REQUEST")
@ToString
public class IntygComplementRequestInformation extends IntygInformation{

	public IntygComplementRequestInformation(
			LocalDateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

}
