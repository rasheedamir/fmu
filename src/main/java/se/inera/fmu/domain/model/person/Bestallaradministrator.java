package se.inera.fmu.domain.model.person;

import javax.persistence.Entity;

import lombok.ToString;

import org.apache.commons.lang.Validate;

@Entity
@ToString
public class Bestallaradministrator extends Person {
    

    //~ Constructors ===================================================================================================

    Bestallaradministrator() {
        //Needed by hibernate
    }

    public Bestallaradministrator(final String name, String befattning, String organisation, String unit, String phone, String email){
    	super(name,  befattning, organisation, unit, phone, email);
    	Validate.notEmpty(organisation);
    }

    //~ Property Methods ===============================================================================================

}
