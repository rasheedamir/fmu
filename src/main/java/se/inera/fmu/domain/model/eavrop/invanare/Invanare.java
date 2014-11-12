package se.inera.fmu.domain.model.eavrop.invanare;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.hibernate.validator.constraints.Email;

import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

/**
 * Created by Rasheed on 7/23/14.
 */
@Entity
@Table(name = "T_INVANARE")
@ToString
public class Invanare extends AbstractBaseEntity implements IEntity<Invanare> {

    //~ Instance fields ================================================================================================

    // database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INVANARE_ID", updatable = false, nullable = false)
    private Long id;

    // business key
    //TODO: replace with value object, maybe this should not be business key and allow several instances of same pnr
    @NotNull
    @Embedded
    private PersonalNumber personalNumber;

    @NotNull
    @Embedded
    private Name name;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @NotNull
    @Embedded
    private Address homeAddress;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SPECIAL_NEEDS")
    private String specialNeeds;

    //~ Constructors ===================================================================================================

    Invanare() {
        // Needed by Hibernate
    }

    public Invanare(PersonalNumber personalNumber, Name name, Gender gender, Address homeAddress, String email, String specialNeeds) {
        this.setPersonalNumber(personalNumber);
        this.setName(name);
        this.setGender(gender);
        this.setHomeAddress(homeAddress);
        this.setEmail(email);
        this.setSpecialNeeds(specialNeeds);
    }

    //~ Property Methods ===============================================================================================

    public Name getName() {
        return name;
    }

    private void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    private void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    private void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
    
    public String getSpecialNeeds() {
		return specialNeeds;
	}

	private void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

    public PersonalNumber getPersonalNumber() {
        return personalNumber;
    }

    private void setPersonalNumber(PersonalNumber personalNumber) {
        this.personalNumber = personalNumber;
    }

    //~ Other Methods ==================================================================================================
    
    @Override
    public boolean sameIdentityAs(final Invanare other) {
        return other != null && this.getPersonalNumber().equals(other.getPersonalNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invanare patient = (Invanare) o;

        if (!this.getPersonalNumber().equals(patient.getPersonalNumber())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalNumber.hashCode();
    }
}
