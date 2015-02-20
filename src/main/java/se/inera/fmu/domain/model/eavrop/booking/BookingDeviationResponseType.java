package se.inera.fmu.domain.model.eavrop.booking;

/**
 * When a specific kind of deviation occurs, the eavrop state is put in 'ON_HOLD' state and
 * the customer must decide if the Eavrop should be restarted or if it should be stopped.
 */
public enum BookingDeviationResponseType {
	RESTART,
	STOP;
}
