package se.inera.fmu.domain.model.eavrop.booking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import se.inera.fmu.domain.shared.ValueObject;

/**
 * Internal id of Booking
 */
@Embeddable
public final class BookingId implements ValueObject<BookingId>, Serializable {

    //~ Instance fields ================================================================================================

	@Column(name = "BOOKING_ID", unique=true, updatable = false, nullable = false, columnDefinition="char(36)")
    protected String id;

    //~ Constructors ===================================================================================================

    BookingId() {
        // Needed by hibernate
    }

    public BookingId(String id) {
        this.setId(id);
    }

    //~ Property Methods ===============================================================================================

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
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

        BookingId other = (BookingId) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean sameValueAs(BookingId other) {
        return other != null && this.id.equals(other.id);
    }
    
    @Override
    public String toString() {
    	return id;
    }
}
