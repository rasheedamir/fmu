package se.inera.fmu.application.impl.command;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.person.Person;

/**
 * Created by Rasheed on 11/4/14.
 *
 * Command to create a new eavrop.
 */
@Getter
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
	public AddBookingDeviationResponseCommand(ArendeId arendeId,
			BookingId bookingId, String response, DateTime responseTimestamp,
			String responseComment, String personName, String personRole,
			String personOrganisation, String personUnit, String personPhone,
			String personEmail) {
		super();
		this.arendeId = arendeId;
		this.bookingId = bookingId;
		this.response = response;
		this.responseTimestamp = responseTimestamp;
		this.responseComment = responseComment;
		this.personName = personName;
		this.personRole = personRole;
		this.personOrganisation = personOrganisation;
		this.personUnit = personUnit;
		this.personPhone = personPhone;
		this.personEmail = personEmail;
		validate();
	}
	
	private void validate(){
		Validate.notNull(this.arendeId);
		Validate.notNull(this.bookingId);
		Validate.notNull(this.response);
		Validate.notNull(this.responseTimestamp);

	}
}