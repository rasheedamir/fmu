package se.inera.fmu.domain.model.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
	
@Entity
@Table(name = "T_PERSON")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISC", discriminatorType=DiscriminatorType.STRING, length=3)
public abstract class Person implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERSON_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ROLE", nullable = true)
    private String role;
    
    @Column(name = "UNIT", nullable = true)
    private String unit;
    
    @Column(name = "ORGANISATION", nullable = true)
    private String organisation;

	@Column(name = "PHONE", nullable = true)
    private String phone;
       
    @Email
    @Column(name = "EMAIL", nullable = true)
    private String email;
    
    //~ Constructors ===================================================================================================

	public Person() {
		//Needed by Hibernate
	}

	public Person(String name, String role, String organisation, String unit, String phone, String email) {
		super();
		this.setNamn(name);
		this.setRole(role);
		this.setUnit(unit);
		this.setOrganisation(organisation);
		this.setPhone(phone);
		this.setEmail(email);


	}


	//~ Property Methods ===============================================================================================

	public String getName() {
		return this.name;
	}

	private void setNamn(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	private void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return this.phone;
	}

	private void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	public String getOrganisation() {
		return organisation;
	}

	private void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	
	public String getUnit() {
		return unit;
	}

	private void setUnit(String unit) {
		this.unit = unit;
	}
}
