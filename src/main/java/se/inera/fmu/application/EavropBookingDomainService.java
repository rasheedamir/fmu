package se.inera.fmu.application;

import org.joda.time.DateTime;

import se.inera.fmu.application.impl.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.CreateBookingCommand;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropBookingDomainService {

	public void createBooking(CreateBookingCommand bookingCommand);
	
	public void changeBookingStatus(ChangeBookingStatusCommand bookingCommand);
	
	public void changeInterpreterBookingStatus(ChangeInterpreterBookingStatusCommand bookingCommand);
	
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand responseCommand );

	
}
