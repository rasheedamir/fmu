package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * The Eavrop is created and get the first state Unassigned. 
 * 
 * The only available behavior is to assign it to a vårdgivarenhet/care giving unit. 
 * Assign will move Eavrop forward into Assigned state.
 * 
 */
@Slf4j
public class UnassignedEavropState extends AbstractEavropState {

	private static final long serialVersionUID = 1L;

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.UNASSIGNED;
	}

	@Override
	public void assignEavropToVardgivarenhet(Eavrop eavrop, Vardgivarenhet vardgivarenhet, HoSPerson assigningPerson) {
		EavropAssignment eavropAssignment = new EavropAssignment(vardgivarenhet, assigningPerson);
		eavrop.setCurrentAssignment(eavropAssignment);
		eavrop.addAssignment(eavropAssignment);

		// State transition UNASSIGNED -> ASSIGNED
		eavrop.setEavropState(new AssignedEavropState());
		log.info("State transition UNASSIGNED -> ASSIGNED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
}
