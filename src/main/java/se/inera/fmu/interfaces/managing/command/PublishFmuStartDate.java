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
 * Command to publish information about start date of FMU.
 */
@Getter
@AllArgsConstructor
public class PublishFmuStartDate {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime startDate;
}
