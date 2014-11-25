package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.person.HoSPerson;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add information about signed and sent intyg to eavrop.
 */
@Getter
@AllArgsConstructor
public class AddIntygSentCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime intygSentDateTime;
	@NonNull private HoSPerson intygSentBy;
}
