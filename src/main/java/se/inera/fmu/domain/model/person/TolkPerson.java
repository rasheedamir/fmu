package se.inera.fmu.domain.model.person;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.ToString;

@Entity
@ToString
public class TolkPerson extends Person {
	
	TolkPerson() {
        //Needed by hibernate
    }
    
    public TolkPerson( final String name, final String organisation){
    	super(name, "Tolk", organisation);
    	
    }
}
