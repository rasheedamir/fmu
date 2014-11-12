package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.ArendeId;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add externally received information about documentation.
 */
@Getter
@AllArgsConstructor
public class AddReceivedExternalDocumentCommand {
	@NonNull private ArendeId arendeId;
	@NonNull private String documentName;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone;
    private String personEmail;
}
