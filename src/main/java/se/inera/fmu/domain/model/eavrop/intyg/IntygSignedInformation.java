package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("SIGNED")
@ToString
public class IntygSignedInformation extends IntygInformation{

	public IntygSignedInformation(LocalDateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

}
