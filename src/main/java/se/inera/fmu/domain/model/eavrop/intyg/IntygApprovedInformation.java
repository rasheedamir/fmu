package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("APPROVED")
@ToString
public class IntygApprovedInformation extends IntygInformation{

	public IntygApprovedInformation(LocalDateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

}
