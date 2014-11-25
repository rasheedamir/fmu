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
 * Command to approve an eavrop.
 */
@Getter
@AllArgsConstructor
public class ApproveEavropCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime approveDateTime;
	@NonNull private Bestallaradministrator bestallaradministrator;
	private String comment;
}
