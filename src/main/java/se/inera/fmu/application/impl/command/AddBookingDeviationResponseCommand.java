package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

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
    @NonNull private String response;
    @NonNull private DateTime responseTimestamp;
    private String responseComment;
    private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone; 
    private String personEmail;
}