package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;

/**
 * Created by Rickard on 11/12/14.
 *
 * Command to approve the compensation regarding an eavrop .
 */
@Getter
@Setter
@AllArgsConstructor
public class ApproveEavropCompensationCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime approveDateTime;
	@NonNull private Boolean approved;
	private String comment;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone;
    private String personEmail;
}
