package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;

/**
 * Service for handling eavrop assignments and the acceptance or rejection of the assignment of the care giving unit
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropAssignmentService {
	
	/**
	 * Assign the eavrop to a care giving unit
	 *  
	 * @param assignCommand
	 */
	public void assignEavropToVardgivarenhet(AssignEavropCommand assignCommand);
	
	/**
	 * The care giving unit accepts the assignment
	 * @param assignCommand
	 */
	public void acceptEavropAssignment(AcceptEavropAssignmentCommand assignCommand);
	
	/**
	 * The care giving unit rejects the assignment
	 * @param assignCommand
	 */
	public void rejectEavropAssignment(RejectEavropAssignmentCommand rejectCommand);
	
}
