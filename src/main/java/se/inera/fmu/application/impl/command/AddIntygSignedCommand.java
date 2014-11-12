package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add information about signed intyg to eavrop.
 */
@Getter
@AllArgsConstructor
public class AddIntygSignedCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime intygSignedDateTime;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone;
    private String personEmail;
}
