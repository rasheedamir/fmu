package se.inera.fmu.application.impl;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.person.Person;

/**
 * Created by Rasheed on 11/4/14.
 *
 * Command to create a new eavrop.
 */
@Getter
@Setter
@AllArgsConstructor
public class AddBookingDeviationResponseCommand {

    private ArendeId arendeId;
    private BookingId bookingId; 
    private String response;
    private DateTime responseTimestamp;
    private String responseComment;
    private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone; 
    private String personEmail; 
}