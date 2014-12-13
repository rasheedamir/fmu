package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignment;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * The Eavrop is created. The only available behavior is to assign it to a vårdgivare/care giver
 * which will then result in an state transition to 'Assigned'
 */
@Slf4j
public class UnassignedEavropState extends AbstractEavropState {

		@Override
		public EavropStateType getEavropStateType() {
			return EavropStateType.UNASSIGNED;
		}
		
		@Override
		public void assignEavropToVardgivarenhet(Eavrop eavrop, Vardgivarenhet vardgivarenhet, HoSPerson assigningPerson ){
			EavropAssignment eavropAssignment = new EavropAssignment(vardgivarenhet, assigningPerson);
			eavrop.setCurrentAssignment(eavropAssignment);
			eavrop.addAssignment(eavropAssignment);
			
			//State transition UNASSIGNED -> ASSIGNED
			eavrop.setEavropState(new AssignedEavropState());
			log.info("State transition UNASSIGNED -> ASSIGNED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
		}
}
