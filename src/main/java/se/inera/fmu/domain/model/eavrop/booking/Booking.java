package se.inera.fmu.domain.model.eavrop.booking;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_BOOKING")
@ToString
public class Booking extends AbstractBaseEntity implements IEntity<Booking>{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// database primary key, using UUID and not a Hibernate sequence
    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    private String id;

    @Column(name = "BOOKING_TYPE", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingType bookingType;

	@NotNull
    @Column(name = "START_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
 	private LocalDateTime startDateTime;

	@NotNull
    @Column(name = "END_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
 	private LocalDateTime endDateTime;

	//TODO: Maybe this should be a status instead 
    @Column(name = "CANCELLATION_TYPE", nullable = true, updatable = true)
    @Enumerated(EnumType.STRING)
    @NotNull
	private BookingDeviationType cancellationReason;

	//TODO: embed?
	@NotNull
	@OneToMany //TODO: maybe many to many if we kan reuse the party entity
 	private Set<Party> parties;

    //~ Constructors ===================================================================================================
    
	Booking(){
		//Needed by Hibernate
	}
	
	public Booking(BookingType type,  LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Party> parties){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(type);
    	Validate.notNull(startDateTime);
    	Validate.notNull(endDateTime);
    	Validate.notNull(parties);
    	this.setBookingType(type);
    	this.setStartDateTime(startDateTime);
    	this.setEndDateTime(endDateTime);
    	this.setParties(parties);
	}
	
    //~ Property Methods ===============================================================================================

	   public String getId() {
			return id;
		}

		private void setId(String id) {
			this.id = id;
		}

		public BookingType getBookingType() {
			return bookingType;
		}

		private void setBookingType(BookingType bookingType) {
			this.bookingType = bookingType;
		}

		public LocalDateTime getStartDateTime() {
			return startDateTime;
		}

		private void setStartDateTime(LocalDateTime startDateTime) {
			this.startDateTime = startDateTime;
		}

		public LocalDateTime getEndDateTime() {
			return endDateTime;
		}

		private void setEndDateTime(LocalDateTime endDateTime) {
			this.endDateTime = endDateTime;
		}

		public Set<Party> getParties() {
			return this.parties;
		}

		private void setParties(Set<Party> parties) {
			this.parties = parties;
		}

		public BookingDeviationType getCancellationReason() {
			return cancellationReason;
		}

		public void setCancellationReason(BookingDeviationType cancellationReason) {
			this.cancellationReason = cancellationReason;
		}
	
	//~ Other Methods ==================================================================================================

	@Override
	public boolean sameIdentityAs(Booking other) {
		return other != null && this.id.equals(other.id);
	}
	

	/**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Booking)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final Booking other = (Booking) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of tracking id.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
