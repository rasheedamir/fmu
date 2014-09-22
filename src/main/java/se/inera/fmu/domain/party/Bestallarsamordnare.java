package se.inera.fmu.domain.party;

import javax.persistence.Entity;

import lombok.ToString;

@Entity
@ToString
public class Bestallarsamordnare extends Party {

	
	Bestallarsamordnare() {
        //Needed by hibernate
    }
    
    public Bestallarsamordnare( final String name, String role, String organisation){
    	super(name, role, organisation);
    }
}
