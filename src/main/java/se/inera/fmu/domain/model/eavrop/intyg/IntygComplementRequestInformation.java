package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.ValueObject;

@Entity
@DiscriminatorValue("REQUEST")
@ToString
public class IntygComplementRequestInformation  extends IntygInformation implements Comparable<IntygComplementRequestInformation>, ValueObject<IntygComplementRequestInformation>{

	public IntygComplementRequestInformation(){
        //Needed by hibernate
    }

	public IntygComplementRequestInformation(
			DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}
	@Override
	public int compareTo(IntygComplementRequestInformation  other) {
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

        return sameValueAs((IntygComplementRequestInformation) o);
    }
	
    @Override
    public boolean sameValueAs(IntygComplementRequestInformation other) {
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
