package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;

/**
 * This Class represents information about the Intyg approval. 
 * When it has been approved and by whom.
 *
 */
@Entity
@DiscriminatorValue("APPROVED")
@ToString
public class IntygApprovedInformation extends IntygInformation{

	public IntygApprovedInformation(){
        //Needed by hibernate
    }
	
	public IntygApprovedInformation(DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}
}
