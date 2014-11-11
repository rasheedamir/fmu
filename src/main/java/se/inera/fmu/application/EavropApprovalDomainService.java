package se.inera.fmu.application;

import se.inera.fmu.application.impl.ApproveEavropCommand;
import se.inera.fmu.application.impl.ApproveEavropCompensationCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropApprovalDomainService {

	public void approveEavrop(ApproveEavropCommand approveCommand);

	public void approveEavropCompensation(ApproveEavropCompensationCommand approveCommand);

	
}
