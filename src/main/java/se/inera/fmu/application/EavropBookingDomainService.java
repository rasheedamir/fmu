package se.inera.fmu.application;

import org.joda.time.DateTime;

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

	public void createBooking(EavropId eavropId, BookingType bookingType, DateTime startDateTime, DateTime endDateTime, Person person, boolean useInterpreter );
	
	public void changeBookingStatus(EavropId eavropId, BookingId bookingId, BookingStatusType bookingStatus);
	
	public void changeBookingStatus(EavropId eavropId, BookingId bookingId, BookingStatusType bookingStatus, Note cancellationNote);
	
	public void changeInterpreterBookingStatus(EavropId eavropId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus);

	public void changeInterpreterBookingStatus(EavropId eavropId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote);

	
}
