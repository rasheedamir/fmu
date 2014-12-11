package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;

/*
 * The Eavrop is assigned to a vårdgivare/care giver. Available behavior is accept and reject.
 * Accept will move Eavrop forward into Accepted state.
 * Reject will move Eavrop back to Unassigned state.
 */
@Slf4j
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
		log.info("State transition ASSIGNED -> ACCEPTED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
	
	@Override
	public void rejectEavropAssignment(Eavrop eavrop, HoSPerson rejectingPerson, String rejectionComment) {
		
		EavropAssignment eavropAssignment = eavrop.getCurrentAssignment();
		eavropAssignment.rejectAssignment(rejectingPerson, rejectionComment);
		
		eavrop.setCurrentAssignment(null);
		
		//State transition ASSIGNED -> UNASSIGNED
		eavrop.setEavropState(new UnassignedEavropState());
		log.info("State transition ASSIGNED -> UNASSIGNED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
	
}