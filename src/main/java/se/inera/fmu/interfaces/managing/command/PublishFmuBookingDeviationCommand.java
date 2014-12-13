package se.inera.fmu.interfaces.managing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.note.Note;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to publish information about response on FMU assignment.
 */
@Getter
@AllArgsConstructor
public class PublishFmuBookingDeviationCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private BookingId bookingId;
	@NonNull private BookingDeviationType bookingDeviationType;
	@NonNull private Boolean bookingDeviationResponseRequired;
	private Note bookingDeviationNote;
	


}
