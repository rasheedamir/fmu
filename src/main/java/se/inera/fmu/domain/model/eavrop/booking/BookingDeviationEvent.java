package se.inera.fmu.domain.model.eavrop.booking;


import lombok.ToString;
import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

@ToString
public class BookingDeviationEvent extends EavropEvent{

	private final BookingId bookingId;
	
	//~ Constructors ===================================================================================================
	public BookingDeviationEvent(final EavropId eavropId, final BookingId bookingId) {
		super(eavropId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}
}
