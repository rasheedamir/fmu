package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.ValueObject;

/**
 * This Class represents information about the Intyg having been created, signed 
 * and sent to the beställare/customer. 
 * When it was sent and by whom.
 *
 */
@Entity
@DiscriminatorValue("SENT")
@ToString
public class IntygSentInformation extends IntygInformation implements Comparable<IntygSentInformation>, ValueObject<IntygSentInformation> {
	
	public IntygSentInformation(){
        //Needed by hibernate
    }

	public IntygSentInformation(DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

	@Override
	public int compareTo(IntygSentInformation  other) {
	        return this.getInformationTimestamp().compareTo(other.getInformationTimestamp());
	}

	@Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        } 
        if (o == null || getClass() != o.getClass()){
        	return false;
        } 

        return sameValueAs((IntygSentInformation) o);
    }
	
    @Override
    public boolean sameValueAs(IntygSentInformation other) {
        return other != null && this.getInformationTimestamp().equals(other.getInformationTimestamp());
    }

	/**
	 * @return HashCode.
	 */
	@Override
	public int hashCode() {
		int result = getInformationTimestamp().hashCode();
		result = 31 * this.getClass().hashCode();
		return result;
	}
}
