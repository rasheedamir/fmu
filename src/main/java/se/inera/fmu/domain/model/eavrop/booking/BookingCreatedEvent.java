package se.inera.fmu.domain.model.eavrop.booking;


import lombok.ToString;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropEvent;

@ToString
public class BookingCreatedEvent extends EavropEvent{
//TODO: Maybe there should be a RebookingEvent only	
	private final BookingId bookingId;
	
	//~ Constructors ===================================================================================================
	public BookingCreatedEvent(final Long eavropId, final BookingId bookingId) {
		super(eavropId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}
}
