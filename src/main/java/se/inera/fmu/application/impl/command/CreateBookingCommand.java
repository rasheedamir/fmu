package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rickard on 11/12/14.
 *
 * Command create a booking on an eavrop.
 */
@Getter
@AllArgsConstructor
@ToString
public class CreateBookingCommand {

    @NonNull private EavropId eavropId;
    @NonNull private BookingType bookingType; 
    @NonNull private DateTime bookingStartDateTime; 
    @NonNull private DateTime bookingEndDateTime;
    @NonNull private String personName;
    @NonNull private String personRole;
    @NonNull private Boolean additionalService;
    @NonNull private Boolean useInterpreter; 
    
}