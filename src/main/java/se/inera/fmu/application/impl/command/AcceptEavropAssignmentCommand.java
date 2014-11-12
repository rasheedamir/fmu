package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
/**
 * Created by Rickard on 11/12/14.
 *
 * Command to accept the assignment of an eavrop.
 */
@Getter
@AllArgsConstructor
public class AcceptEavropAssignmentCommand {
	@NonNull private EavropId eavropId;
	@NonNull private HsaId hsaId;
	
}
