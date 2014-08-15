package se.inera.fmu.domain.model.patient;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Rasheed on 7/26/14.
 *
 */
@ToString
@Embeddable
public final class Name implements ValueObject<Name> {

    //~ Instance fields ================================================================================================

    @Column(name = "initials", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Initials initials;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Size(min = 0, max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    //~ Constructors ===================================================================================================

    Name() {
        // Needed by Hibernate
    }

    public Name(Initials initials, String firstName, String middleName, String lastName) {
        this.setInitials(initials);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
    }

    //~ Property Methods ===============================================================================================

    public Initials getInitials() {
        return initials;
    }

    private void setInitials(Initials initials) {
        this.initials = initials;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //~ Other Methods ==================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Name name = (Name) o;

        if (firstName != null ? !firstName.equals(name.firstName) : name.firstName != null) return false;
        if (initials != name.initials) return false;
        if (lastName != null ? !lastName.equals(name.lastName) : name.lastName != null) return false;
        if (!middleName.equals(name.middleName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = initials != null ? initials.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + middleName.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean sameValueAs(Name other) {
        return other != null && this.equals(other);
    }
}
