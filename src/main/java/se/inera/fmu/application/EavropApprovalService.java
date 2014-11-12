package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropApprovalService {

	public void approveEavrop(ApproveEavropCommand approveCommand);

	public void approveEavropCompensation(ApproveEavropCompensationCommand approveCommand);
}
