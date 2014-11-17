package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;

/*
 * The Eavrop is assigned to a vårdgivare/care giver. Available behavior is accept and reject.
 * Accept will move Eavrop forward into Accepted state.
 * Reject will move Eavrop back to Unassigned state.
 */
public class AssignedEavropState extends AbstractEavropState {
		
	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.ASSIGNED;
	}
	
	@Override
	public void acceptEavropAssignment(Eavrop eavrop){
		eavrop.getCurrentAssignment().acceptAssignment();
	
		//State transition ASSIGNED -> ACCEPTED
		eavrop.setEavropState(new AcceptedEavropState());
	}
	
	@Override
	public void rejectEavropAssignment(Eavrop eavrop) {
		
		EavropAssignment eavropAssignment = eavrop.getCurrentAssignment();
		eavropAssignment.rejectAssignment();
		
		eavrop.setCurrentAssignment(null);
		
		//State transition ASSIGNED -> UNASSIGNED
		eavrop.setEavropState(new UnassignedEavropState());
	}
}