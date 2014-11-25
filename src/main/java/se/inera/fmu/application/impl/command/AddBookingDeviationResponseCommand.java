package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.person.Bestallaradministrator;

/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add a deviation response to a boking on a eavrop.
 */
@Getter
@AllArgsConstructor
public class AddBookingDeviationResponseCommand {

    @NonNull private ArendeId arendeId;
    @NonNull private BookingId bookingId; 
    @NonNull private BookingDeviationResponseType responseType;
    @NonNull private DateTime responseTimestamp;
    private String responseComment;
    private Bestallaradministrator bestallaradministrator;
}