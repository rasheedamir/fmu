package se.inera.fmu.domain.model.eavrop.booking;


import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

public class BookingCreatedEvent extends EavropEvent{
//TODO: Maybe there should be a RebookingEvent only	
	private final String bookingId;
	
	//~ Constructors ===================================================================================================
	public BookingCreatedEvent(final ArendeId arendeId, final String bookingId) {
		super(arendeId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public String getBookingId() {
		return this.bookingId;
	}
}
