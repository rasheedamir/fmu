package se.inera.fmu.domain.model.person;

import javax.persistence.Entity;

import lombok.ToString;

@Entity
@ToString
public class HoSPerson extends Person {
	
	HoSPerson() {
        //Needed by hibernate
    }
	public HoSPerson( final String name, String role, String organisation, String unit){
		this(name, role, organisation, unit, null, null);
	}
	
    public HoSPerson( final String name, String role, String organisation, String unit, String phone, String email){
    	super(name, role, organisation, unit, phone, email);
    }

}
