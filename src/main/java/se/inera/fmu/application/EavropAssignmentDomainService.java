package se.inera.fmu.application;

import se.inera.fmu.application.impl.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.AssignEavropCommand;
import se.inera.fmu.application.impl.RejectEavropAssignmentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropAssignmentDomainService {
	
	public void assignEavropToVardgivarenhet(AssignEavropCommand assignCommand);
	
	public void acceptEavropAssignment(AcceptEavropAssignmentCommand assignCommand);
	
	public void rejectEavropAssignment(RejectEavropAssignmentCommand rejectCommand);
	
}
