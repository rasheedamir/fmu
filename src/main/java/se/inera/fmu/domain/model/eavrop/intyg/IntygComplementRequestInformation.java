package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("REQUEST")
@ToString
public class IntygComplementRequestInformation extends IntygInformation{

	public IntygComplementRequestInformation(){
        //Needed by hibernate
    }

	public IntygComplementRequestInformation(
			DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

}
