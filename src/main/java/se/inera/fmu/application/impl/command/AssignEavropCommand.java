package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
/**
 * Created by Rickard on 11/12/14.
 *
 * Command to assign an eavrop to a vardgivarenhet.
 */
@Getter
@AllArgsConstructor
public class AssignEavropCommand {
	@NonNull private EavropId eavropId;
	@NonNull private HsaId vardgivarenhetHsaId;
	@NonNull private HsaId personHsaId;
    @NonNull private String personName;
    @NonNull private String personRole;
    private String personOrganisation;
    private String personUnit;
}
