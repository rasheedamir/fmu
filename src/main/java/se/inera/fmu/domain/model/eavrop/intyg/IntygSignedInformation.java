package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("SIGNED")
@ToString
public class IntygSignedInformation extends IntygInformation implements Comparable<IntygSignedInformation> {
	
	public IntygSignedInformation(){
        //Needed by hibernate
    }

	public IntygSignedInformation(DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

	@Override
	public int compareTo(IntygSignedInformation  other) {
	        return this.getInformationTimestamp().compareTo(other.getInformationTimestamp());
	}
}
