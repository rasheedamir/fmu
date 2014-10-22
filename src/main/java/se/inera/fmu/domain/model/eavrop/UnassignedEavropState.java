package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.AssignedEavropState;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;

/**
 * The Eavrop is created. The only available behavior is to assign it to a vårdgivare/care giver
 * which will then result in an state transition to 'Assigned'
 */
public class UnassignedEavropState extends AbstractEavropState {

		@Override
		public EavropStateType getEavropStateType() {
			return EavropStateType.UNASSIGNED;
		}
		
		@Override
		public void assignEavropToVardgivarenhet(Eavrop eavrop, Vardgivarenhet vardgivarenhet){
			EavropAssignment eavropAssignment = new EavropAssignment(vardgivarenhet);
			eavrop.setCurrentAssignment(eavropAssignment);
			eavrop.addAssignment(eavropAssignment);
			
			eavrop.handleEavropAssignedToVardgivarenhet();
			
			//State transition UNASSIGNED -> ASSIGNED
			eavrop.setEavropState(new AssignedEavropState());
		}
}
