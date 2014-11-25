package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.person.Bestallaradministrator;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add information about an intyg connected to an eavrop have been approved.
 */
@Getter
@AllArgsConstructor
public class AddIntygApprovedCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime intygApprovedDateTime;
	private Bestallaradministrator bestallaradministrator;
}
