package se.inera.fmu.domain.party;

import javax.persistence.Entity;

import lombok.ToString;

@Entity
@ToString
public class HoSParty extends Party {

	
	
	HoSParty() {
        //Needed by hibernate
    }
    
    public HoSParty( final String name, String role, String organisation){
    	super(name, role, organisation);
    	
    }
}
