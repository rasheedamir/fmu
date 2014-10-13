package se.inera.fmu.domain.model.eavrop.booking;


import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class BookingCreatedEvent extends EavropEvent{
//TODO: Maybe there should be a RebookingEvent only	
	private final BookingId bookingId;
	
	//~ Constructors ===================================================================================================
	public BookingCreatedEvent(final ArendeId arendeId, final BookingId bookingId) {
		super(arendeId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}
}
