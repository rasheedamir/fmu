package se.inera.fmu.domain.model.eavrop.booking;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_BOOKING")
@ToString
public class Booking extends AbstractBaseEntity implements IEntity<Booking> {

	private static final long serialVersionUID = 1L;

	// database primary key, using UUID and not a Hibernate sequence
	@EmbeddedId
	@Column(name = "BOOKING_ID", updatable = false, nullable = false)
	private BookingId bookingId;

	@Column(name = "BOOKING_TYPE", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private BookingType bookingType;

	@NotNull
	@Column(name = "START_DATETIME", nullable = false, updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startDateTime;

	@NotNull
	@Column(name = "END_DATETIME", nullable = false, updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endDateTime;

	@OneToMany(cascade = CascadeType.ALL)
	// TODO: maybe many to many if we kan reuse the person entity
	@JoinTable(name = "R_BOOKING_PERSON", joinColumns = @JoinColumn(name = "BOOKING_ID"), inverseJoinColumns = @JoinColumn(name = "PERSON_ID"))
	private Set<Person> persons; // value object

	@Column(name = "BOOKING_STATUS_TYPE", nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	@NotNull
	private BookingStatusType bookingStatusType;

//	@Embedded
//	private BookingDeviation bookingDeviation;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DEVIATION_NOTE_ID", nullable = true)
	private Note deviationNote;
    
    //TODO, should we have a person which defines who set the deviation?
    
    @Embedded
    private BookingDeviationResponse bookingDeviationResponse; 

	@Embedded
	private InterpreterBooking interpreterBooking;

	// ~ Constructors
	// ===================================================================================================

	Booking() {
		// Needed by Hibernate
	}

	public Booking(BookingType type, DateTime startDateTime,
			DateTime endDateTime, Person person, boolean useInterpreter) {
		this.setBookingId(new BookingId(UUID.randomUUID().toString()));
		this.bookingStatusType = BookingStatusType.BOOKED;
		Validate.notNull(type);
		Validate.notNull(startDateTime);
		Validate.notNull(endDateTime);
		Validate.notNull(person);
		this.setBookingType(type);
		this.setStartDateTime(startDateTime);
		this.setEndDateTime(endDateTime);
		this.addPerson(person);
		if (useInterpreter) {
			this.interpreterBooking = new InterpreterBooking();
		}
	}

	// ~ Property Methods
	// ===============================================================================================

	public BookingId getBookingId() {
		return bookingId;
	}

	private void setBookingId(BookingId id) {
		this.bookingId = id;
	}

	public BookingType getBookingType() {
		return bookingType;
	}

	private void setBookingType(BookingType bookingType) {
		this.bookingType = bookingType;
	}

	public BookingStatusType getBookingStatus() {
		return bookingStatusType;
	}

	public void setBookingStatus(BookingStatusType bookingStatusType) {
		this.bookingStatusType = bookingStatusType;
	}

	public Note getDeviationNote() {
		return deviationNote;
	}

	public void setDeviationNote(Note deviationNote) {
		this.deviationNote = deviationNote;
	}

	
	public DateTime getStartDateTime() {
		return startDateTime;
	}

	private void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public DateTime getEndDateTime() {
		return endDateTime;
	}

	private void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Set<Person> getPersons() {
		return this.persons;
	}

	private void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	public void addPerson(Person person) {
		if (this.persons == null) {
			this.persons = new HashSet<Person>();
		}
		this.persons.add(person);
	}

//	public BookingDeviation getBookingDeviation() {
//		return this.bookingDeviation;
//	}
//
//	public void setBookingDeviation(BookingDeviation bookingDeviation) {
//		this.bookingDeviation = bookingDeviation;
//	}

	
	public InterpreterBooking getInterpreterBooking() {
		return interpreterBooking;
	}

	public BookingDeviationResponse getBookingDeviationResponse() {
		return bookingDeviationResponse;
	}

	public void setBookingDeviationResponse(BookingDeviationResponse bookingDeviationResponse) {
		this.bookingDeviationResponse = bookingDeviationResponse;
	}

	private void setInterpreterBooking(InterpreterBooking interpreterBooking) {
		this.interpreterBooking = interpreterBooking;
	}
	
	public boolean hasDeviation(){
		return getBookingStatus().isCancelled();
	}

	public boolean hasInterpreterDeviation(){
		return (getInterpreterBooking()!=null)?getInterpreterBooking().hasDeviation():Boolean.FALSE;
	}

	// ~ Other Methods
	// ==================================================================================================

	@Override
	public boolean sameIdentityAs(Booking other) {
		return other != null && this.bookingId.equals(other.bookingId);
	}

	/**
	 * @param object
	 *            to compare
	 * @return True if they have the same identity
	 * @see #sameIdentityAs(Booking)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Booking other = (Booking) object;
		return sameIdentityAs(other);
	}

	/**
	 * @return Hash code of tracking id.
	 */
	@Override
	public int hashCode() {
		return bookingId.hashCode();
	}
}
