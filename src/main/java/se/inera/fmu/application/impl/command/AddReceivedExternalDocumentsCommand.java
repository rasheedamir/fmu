package se.inera.fmu.application.impl.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.person.Bestallaradministrator;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add externally received information about documentation.
 */
@Getter
@AllArgsConstructor
public class AddReceivedExternalDocumentsCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private DateTime documentsSentDateTime;
	@NonNull private List<String> documentNames;
	private Bestallaradministrator bestallaradministrator;
}
