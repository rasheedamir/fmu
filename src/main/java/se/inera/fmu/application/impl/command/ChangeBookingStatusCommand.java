package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rickard on 11/12/14.
 *
 * Command to change the status of a booking connected to an eavrop.
 */
@Getter
@AllArgsConstructor
public class ChangeBookingStatusCommand {

    @NonNull private EavropId eavropId;
    @NonNull private BookingId bookingId; 
    @NonNull private BookingStatusType bookingStatus; 
	private String comment;
	private HsaId personHsaId;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;

}