package se.inera.fmu.application;

import java.util.List;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropBookingDomainService {

	public void createBooking(ArendeId arendeId, BookingType bookingType, DateTime startDateTime, DateTime endDateTime, Person person, boolean useInterpreter );
	
	public void changeBookingStatus(ArendeId arendeId, BookingId bookingId, BookingStatusType bookingStatus);
	
	public void changeBookingStatus(ArendeId arendeId, BookingId bookingId, BookingStatusType bookingStatus, Note cancellationNote);
	
	public void changeInterpreterBookingStatus(ArendeId arendeId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus);

	public void changeInterpreterBookingStatus(ArendeId arendeId, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote);

	
}
