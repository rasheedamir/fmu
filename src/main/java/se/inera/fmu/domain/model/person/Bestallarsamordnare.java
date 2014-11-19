package se.inera.fmu.domain.model.person;

import javax.persistence.Entity;

import lombok.ToString;

@Entity
@ToString
public class Bestallarsamordnare extends Person {
	
	Bestallarsamordnare() {
        //Needed by hibernate
    }
    
    public Bestallarsamordnare(final String name, String role, String organisation, String unit){
    	super(name, role, organisation, unit, null, null);
    }
    
    public Bestallarsamordnare(final String name, String role, String organisation, String unit, String phone, String email){
    	super(name, role, organisation, unit, phone, email);
    }
    
}
