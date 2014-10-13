package se.inera.fmu.domain.model.person;

import javax.persistence.Entity;

import lombok.ToString;

@Entity
@ToString
public class HoSPerson extends Person {
	
	HoSPerson() {
        //Needed by hibernate
    }
    
    public HoSPerson( final String name, String role, String organisation){
    	super(name, role, organisation);
    }

}
