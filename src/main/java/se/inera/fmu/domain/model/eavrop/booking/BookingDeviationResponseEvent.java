package se.inera.fmu.domain.model.eavrop.booking;

import se.inera.fmu.domain.model.eavrop.EavropEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;

/**
 * Domain event that states that a response to a booking deviation has been made.
 */
public class BookingDeviationResponseEvent extends EavropEvent{
	
	//~ Constructors ===================================================================================================
	public BookingDeviationResponseEvent(final EavropId eavropId) {
		super(eavropId);
	}

	//~ Property Methods ===============================================================================================

}
