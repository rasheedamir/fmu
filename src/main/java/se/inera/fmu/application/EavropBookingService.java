package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

/**
 * Service for handling eavrop bookings and events related to bookings
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropBookingService {

	/**
	 * Creates and adds a booking to the eavrop
	 * @param bookingCommand
	 */
	public BookingId createBooking(CreateBookingCommand bookingCommand);
	
	/**
	 * Changes the status of a booking.
	 * @param bookingCommand
	 */
	public void changeBookingStatus(ChangeBookingStatusCommand bookingCommand);
	
	/**
	 * Changes the status of an interpreter booking.
	 * @param bookingCommand
	 */
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand bookingCommand);
	
	/**
	 * Add the response of a booking deviation
	 * @param responseCommand
	 */
	public EavropId addBookingDeviationResponse(AddBookingDeviationResponseCommand responseCommand );
}
