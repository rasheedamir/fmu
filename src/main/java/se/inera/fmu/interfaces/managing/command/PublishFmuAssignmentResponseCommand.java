package se.inera.fmu.interfaces.managing.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to publish information about response on FMU assignment.
 */
@Getter
@AllArgsConstructor
public class PublishFmuAssignmentResponseCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private Boolean accepted;
	@NonNull private String unit;
	@NonNull private String organisation;
	@NonNull private String postalAddress;
	@NonNull private String postalCode;
	@NonNull private String city;
	private String country;
	private String phone;
	private String emailAddress;
}
