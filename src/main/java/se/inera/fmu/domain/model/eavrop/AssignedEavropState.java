package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;

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
	public void acceptEavropAssignment(Eavrop eavrop, HoSPerson acceptingPerson ){
		eavrop.getCurrentAssignment().acceptAssignment(acceptingPerson);
	
		//State transition ASSIGNED -> ACCEPTED
		eavrop.setEavropState(new AcceptedEavropState());
	}
	
	@Override
	public void rejectEavropAssignment(Eavrop eavrop, HoSPerson rejectingPerson) {
		
		EavropAssignment eavropAssignment = eavrop.getCurrentAssignment();
		eavropAssignment.rejectAssignment(rejectingPerson);
		
		eavrop.setCurrentAssignment(null);
		
		//State transition ASSIGNED -> UNASSIGNED
		eavrop.setEavropState(new UnassignedEavropState());
	}
	
}