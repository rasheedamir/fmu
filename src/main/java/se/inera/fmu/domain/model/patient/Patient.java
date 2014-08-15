package se.inera.fmu.domain.model.patient;

import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.shared.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Rasheed on 7/23/14.
 */
@Entity
@Table(name = "T_PATIENT")
@ToString
public class Patient extends AbstractAuditingEntity implements IEntity<Patient> {

    //~ Instance fields ================================================================================================

    // database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id", updatable = false, nullable = false)
    private Long patientId;

    // business key
    @NotNull
    @Column(name = "personal_number", updatable = false, nullable = false, unique = true)
    private String personalNumber;

    @NotNull
    @Embedded
    private Name name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @NotNull
    @Embedded
    private Address homeAddress;

    @Email
    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "patient")
    private Set<Eavrop> eavrops;

    //~ Constructors ===================================================================================================

    Patient() {
        // Needed by Hibernate
    }

    public Patient(String personalNumber, Name name, Gender gender, Address homeAddress, String email) {
        this.setPersonalNumber(personalNumber);
        this.setName(name);
        this.setGender(gender);
        this.setHomeAddress(homeAddress);
        this.setEmail(email);
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

    public Set<Eavrop> getEavrops() {
        return eavrops;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    private void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    //~ Other Methods ==================================================================================================

    @Override
    public boolean sameIdentityAs(final Patient other) {
        return other != null && this.getPersonalNumber().equals(other.getPersonalNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (!this.getPersonalNumber().equals(patient.getPersonalNumber())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalNumber.hashCode();
    }
}
