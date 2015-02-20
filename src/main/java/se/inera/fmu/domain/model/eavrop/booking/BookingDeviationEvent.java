package se.inera.fmu.domain.model.eavrop.booking;


import lombok.ToString;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * Domain event that states that a deviation has occurred on a booking.
 */
@ToString
public class BookingDeviationEvent extends EavropEvent{

	private final ArendeId arendeId;
	private final BookingId bookingId;
	private final BookingDeviationType bookingDeviationType;
	private boolean bookingDeviationResponseRequired;
	private final Note bookingDeviationNote;
	
	//~ Constructors ===================================================================================================
	public BookingDeviationEvent(final EavropId eavropId, final ArendeId arendeId, final BookingId bookingId, final BookingDeviationType bookingDeviationType, boolean isBookingDevationResponseRequired, final Note bookingDeviationNote) {
		super(eavropId);
		this.arendeId = arendeId;
		this.bookingId = bookingId;
		this.bookingDeviationType = bookingDeviationType;
		this.bookingDeviationResponseRequired = isBookingDevationResponseRequired;
		this.bookingDeviationNote = bookingDeviationNote;
	}

	//~ Property Methods ===============================================================================================

	public ArendeId getArendeId() {
		return arendeId;
	}

	public BookingId getBookingId() {
		return this.bookingId;
	}

	public BookingDeviationType getBookingDeviationType() {
		return bookingDeviationType;
	}

	public boolean isBookingDeviationResponseRequired() {
		return bookingDeviationResponseRequired;
	}

	public Note getBookingDeviationNote() {
		return bookingDeviationNote;
	}

	
}
