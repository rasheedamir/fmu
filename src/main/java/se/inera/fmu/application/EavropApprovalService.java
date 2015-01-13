package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;

/**
 * Service for handling eavrop approvals
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropApprovalService {

	/**
	 * Approves an eavrop
	 * 
	 * @param approveCommand
	 */
	public void approveEavrop(ApproveEavropCommand approveCommand);

	/**
	 * Approves the compensation part of the eavrop
	 * @param approveCommand
	 */
	public void approveEavropCompensation(ApproveEavropCompensationCommand approveCommand);
}
