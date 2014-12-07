package se.inera.fmu.interfaces.managing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to publish information about response on FMU assignment.
 */
@Getter
@AllArgsConstructor
public class PublishFmuDocumentRequestedCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private String documentRequest;
	@NonNull private DateTime requestedDateTime;
	@NonNull private Person requestedBy;
	private Note requestNote;
	


}
