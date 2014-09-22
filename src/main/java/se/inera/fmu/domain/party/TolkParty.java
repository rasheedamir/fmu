package se.inera.fmu.domain.party;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.ToString;

@Entity
@ToString
public class TolkParty extends Party {

	
	
	TolkParty() {
        //Needed by hibernate
    }
    
    public TolkParty( final String name, String organisation){
    	super(name, "Tolk", organisation);
    	
    }
}
