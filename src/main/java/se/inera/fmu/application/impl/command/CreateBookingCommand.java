package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;

/**
 * Created by Rasheed on 11/4/14.
 *
 * Command to create a new booking.
 */
@Getter
@Setter
@AllArgsConstructor
public class CreateBookingCommand {

    private EavropId eavropId;
    private BookingType bookingType; 
    private DateTime bookingStartDateTime; 
    private DateTime bookingEndDateTime; 
    private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private boolean useInterpreter; 
}