package se.inera.fmu.interfaces.managing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to publish information about response on FMU assignment.
 */
@Getter
@AllArgsConstructor
public class PublishFmuBookingCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private BookingId bookingId;
	@NonNull private BookingType bookingType;
	@NonNull private DateTime startDateTime;
	@NonNull private DateTime endDateTime;
	@NonNull private String resourceName;
	@NonNull private String resourceRole;
	@NonNull private Boolean interpreterBooked;

}
