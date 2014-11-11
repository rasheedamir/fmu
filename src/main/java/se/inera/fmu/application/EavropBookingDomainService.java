package se.inera.fmu.application;

import se.inera.fmu.application.impl.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.CreateBookingCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropBookingDomainService {

	public void createBooking(CreateBookingCommand bookingCommand);
	
	public void changeBookingStatus(ChangeBookingStatusCommand bookingCommand);
	
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand bookingCommand);
	
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand responseCommand );

	
}
