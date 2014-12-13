package se.inera.fmu.domain.model.eavrop.booking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.ToString;
import se.inera.fmu.domain.shared.ValueObject;

/**
 * Created by Rickard on 9/26/14.
 *
 */
@ToString
@Embeddable
public final class BookingResource implements ValueObject<BookingResource>, Serializable {

    //~ Instance fields ================================================================================================

	@Column(name = "BOOKING_RESOURCE_NAME")
    private String name;

	@Column(name = "BOOKING_RESOURCE_ROLE")
    private String role;

    //~ Constructors ===================================================================================================

    BookingResource() {
        // Needed by hibernate
    }

    public BookingResource(final String name, final String role) {
        this.setName(name);
        this.setRole(role);
    }

    //~ Property Methods ===============================================================================================

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return this.role;
    }

    private void setRole(String role) {
        this.role = role;
    }

    //~ Other fields ===================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }

        BookingResource other = (BookingResource) o;
        if (name != null ? !name.equals(other.name) : other.name != null){
        	return false;
        }
        if (role != null ? !role.equals(other.role) : other.role != null){
        	return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;

    }

    @Override
    public boolean sameValueAs(BookingResource other) {
        return other != null && this.equals(other);
    }

}
