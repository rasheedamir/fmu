package se.inera.fmu.domain.model.eavrop.booking.interpreter;


import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

public class InterpreterBookingDeviationEvent extends EavropEvent{

	private final BookingId bookingId;
	
	//~ Constructors ===================================================================================================
	public InterpreterBookingDeviationEvent(final EavropId eavropId, final BookingId bookingId) {
		super(eavropId);
		this.bookingId = bookingId;
	}

	//~ Property Methods ===============================================================================================

	public BookingId getBookingId() {
		return this.bookingId;
	}
}
