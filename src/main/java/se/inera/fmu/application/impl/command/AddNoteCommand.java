package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;


/**
 * Created by Rickard on 11/12/14.
 *
 * Command to add a note to an eavrop.
 */
@Getter
@AllArgsConstructor
public class AddNoteCommand {
	@NonNull private EavropId eavropId;
	@NonNull private String text;
	@NonNull private HsaId personHsaId;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
}
