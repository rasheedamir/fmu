package se.inera.fmu.domain.party;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.model.bestallare.Bestallare;
import se.inera.fmu.domain.model.bestallare.PersonalId;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
	
@Entity
@Table(name = "T_PARTY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Party implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String namn;

    @Column(name = "ROLE", nullable = true)
    private String role;
    
    @Column(name = "ORGANISATION", nullable = true)
    private String organistation;

    
    //~ Constructors ===================================================================================================

	public Party() {
		//Needed by Hibernate
	}

	public Party(String namn, String role, String organistation) {
		super();
		this.namn = namn;
		this.role = role;
		this.organistation = organistation;
	}


	//~ Property Methods ===============================================================================================

	public String getNamn() {
		return namn;
	}

	private void setNamn(String namn) {
		this.namn = namn;
	}

	public String getRole() {
		return role;
	}

	private void setRole(String role) {
		this.role = role;
	}

	public String getOrganistation() {
		return organistation;
	}

	private void setOrganistation(String organistation) {
		this.organistation = organistation;
	}
    

	
	
	
	
}
