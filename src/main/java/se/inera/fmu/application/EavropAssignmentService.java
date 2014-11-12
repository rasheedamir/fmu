package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropAssignmentService {
	
	public void assignEavropToVardgivarenhet(AssignEavropCommand assignCommand);
	
	public void acceptEavropAssignment(AcceptEavropAssignmentCommand assignCommand);
	
	public void rejectEavropAssignment(RejectEavropAssignmentCommand rejectCommand);
	
}
