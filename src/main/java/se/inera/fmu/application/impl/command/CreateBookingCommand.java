package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;

/**
 * Created by Rickard on 11/12/14.
 *
 * Command create a booking on an eavrop.
 */
@Getter
@AllArgsConstructor
public class CreateBookingCommand {

    @NonNull private EavropId eavropId;
    @NonNull private BookingType bookingType; 
    @NonNull private DateTime bookingStartDateTime; 
    @NonNull private DateTime bookingEndDateTime; 
    @NonNull private String personName;
    private boolean additionalService;
    @NonNull private String personRole;
    private String personOrganisation;
    private String personUnit;
    private boolean useInterpreter; 
    
    @Override
    public String toString() {
    	return "EavropID: " + eavropId + " bookingType: " + bookingType + " StartDateTime: " + bookingStartDateTime +
    			"EndDateTime: " + bookingEndDateTime + " Person Name: " + personName + " Role: " + personRole +
    			"Organisation: " + personOrganisation + " Unit: " + personUnit + " Tolk: " + useInterpreter;
    }
}