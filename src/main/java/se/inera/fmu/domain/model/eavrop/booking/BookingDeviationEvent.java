package se.inera.fmu.domain.model.eavrop.booking;


import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class BookingDeviationEvent extends EavropEvent{

	private final BookingId bookingId;
	
	//~ Constructors ===================================================================================================
	public BookingDeviationEvent(final ArendeId arendeId, final BookingId bookingId) {
		super(arendeId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}
}
