package se.inera.fmu.domain.model.eavrop.invanare;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

/**
 *
 */
@ToString
@Embeddable
public final class PersonalNumber implements ValueObject<PersonalNumber>, Serializable {

    //~ Instance fields ================================================================================================

    @Column(name = "PERSONAL_NUMBER", updatable = false, nullable = false)
    protected String personalNumber;

    //~ Constructors ===================================================================================================

    PersonalNumber() {
        // Needed by hibernate
    }

    public PersonalNumber(String personalNumber) {
    	
    	//TODO: Validate personal number
        this.setPersonalNumber(personalNumber);
    }

    //~ Property Methods ===============================================================================================

    public String getPersonalNumber() {
        return this.personalNumber;
    }

    private void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    //~ Other fields ===================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }
        PersonalNumber other = (PersonalNumber) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return personalNumber.hashCode();
    }

    @Override
    public boolean sameValueAs(PersonalNumber other) {
        return other != null && this.personalNumber.equals(other.personalNumber);
    }
}
