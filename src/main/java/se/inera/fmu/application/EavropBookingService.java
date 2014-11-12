package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropBookingService {

	public void createBooking(CreateBookingCommand bookingCommand);
	
	public void changeBookingStatus(ChangeBookingStatusCommand bookingCommand);
	
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand bookingCommand);
	
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand responseCommand );

	
}
