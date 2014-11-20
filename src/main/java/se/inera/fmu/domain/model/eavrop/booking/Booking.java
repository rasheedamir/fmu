package se.inera.fmu.domain.model.eavrop.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.converter.BooleanToStringConverter;
import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.eavrop.InterpreterBookingEventDTO;
import se.inera.fmu.domain.model.eavrop.UtredningType;
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

	@Column(name = "ADDITIONAL_SERVICE", nullable = false, updatable = false)
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean additionalService;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BOOKING_PERSON_ID")
	private Person person;

	@Column(name = "BOOKING_STATUS_TYPE", nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	@NotNull
	private BookingStatusType bookingStatusType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DEVIATION_NOTE_ID", nullable = true)
	private Note deviationNote;
    
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
			DateTime endDateTime, boolean additionalService, 
			Person person, boolean useInterpreter) {
		this.setBookingId(new BookingId(UUID.randomUUID().toString()));
		this.setBookingStatus(BookingStatusType.BOOKED);
		Validate.notNull(type);
		Validate.notNull(startDateTime);
		Validate.notNull(endDateTime);
		Validate.notNull(person);
		this.setBookingType(type);
		this.setStartDateTime(startDateTime);
		this.setEndDateTime(endDateTime);
		this.setAdditionalService(additionalService);
		this.setPerson(person);
		if (useInterpreter) {
			this.setInterpreterBooking(new InterpreterBooking());
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

	public Boolean isAdditionalService() {
		return additionalService;
	}

	private void setAdditionalService(Boolean additionalService) {
		this.additionalService = additionalService;
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
	
	public long getBookingDuration(){
		if(getStartDateTime() !=null && getEndDateTime() !=null && getStartDateTime().isBefore(getEndDateTime())){
			return getStartDateTime().getMillis() - getEndDateTime().getMillis();
		}
		return 0L;
	}

	public Person getPerson() {
		return this.person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}


	public BookingDeviationResponse getBookingDeviationResponse() {
		return bookingDeviationResponse;
	}

	public void setBookingDeviationResponse(BookingDeviationResponse bookingDeviationResponse) {
		this.bookingDeviationResponse = bookingDeviationResponse;
	}

	public boolean hasDeviation(){
		return getBookingStatus().isDeviant();
	}

	public InterpreterBooking getInterpreterBooking() {
		return interpreterBooking;
	}

	private void setInterpreterBooking(InterpreterBooking interpreterBooking) {
		this.interpreterBooking = interpreterBooking;
	}

	public boolean hasInterpreterBooking(){
		return (getInterpreterBooking()!=null);
	}
	
	public boolean hasInterpreterDeviation(){
		return (getInterpreterBooking()!=null)?getInterpreterBooking().hasDeviation():Boolean.FALSE;
	}
	
	public List<EavropEventDTO> getAsEavropEvents(UtredningType utredningType){
		List<EavropEventDTO> events= new ArrayList<EavropEventDTO>();
		
		events.add(getAsEavropEvent(utredningType));
		
		if(getBookingDeviationResponse()!=null){
			events.add(getBookingDeviationResponse().getAsEavropEvent());
		}
		
		return events;
	}
	
	private EavropEventDTO getAsEavropEvent(UtredningType utredningType) {
		String comment = (this.deviationNote!=null)?this.deviationNote.getText():null;
		List<BookingStatusType> validBookingStatuses =  BookingStatusType.getValidBookingStatuses(utredningType, getBookingType());
		InterpreterBookingEventDTO interpreterBookingEventDTO = (getInterpreterBooking()!=null)?getInterpreterBooking().getAsInterpreterBookingEventDTO():null; 
		
		return (this.getPerson()!=null)?
			new EavropEventDTO(getEavropEventDTOType(), this.startDateTime, this.bookingStatusType, comment, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit(),validBookingStatuses,interpreterBookingEventDTO):
			new EavropEventDTO(getEavropEventDTOType(), this.startDateTime, this.bookingStatusType, comment, null, null, null, null,validBookingStatuses,interpreterBookingEventDTO);
	}
	
	private EavropEventDTOType getEavropEventDTOType(){
		BookingType type = getBookingType();
		
		switch(type) {
		case EXAMINATION:
            return EavropEventDTOType.BOOKING_EXAMINATION; 
        case BREIFING_WITH_CITIZEN:
        	return EavropEventDTOType.BOOKING_BREIFING_WITH_CITIZEN;
        case INTERNAL_WORK:
        	return EavropEventDTOType.BOOKING_INTERNAL_WORK;
        default:
        	return EavropEventDTOType.UNKNOWN;
		}
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
