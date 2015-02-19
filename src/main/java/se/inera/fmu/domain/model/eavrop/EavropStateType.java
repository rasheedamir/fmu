package se.inera.fmu.domain.model.eavrop;

import java.util.Arrays;


/**
 * This Enum defines the state types that an Eavrop can have 
 * UNASSIGNED: Created not assigned to any care giver unit or assigned care giver unit has declined the assignment.
 * ASSIGNED: Assigned to a care giving unit, awaiting response from the care giving unit
 * ACCEPTED: The assignment has been accepted and will be performed by the assigned care giving unit
 * ONGOING: The Eavrop is in Accepted state but also the start date has been set, by receiving external documents, and passed.
 * ON_HOLD: The Eavrop, has a serious deviation so the customer must decide if the Eavrop should be restarted or closed 
 * SENT: Intyg has been created, signed and sent to customer by external application
 * APPROVED: The eavrop have been approved/accepted by the customer 
 * CLOSED: The eavrop compensation has been approved or deviation response from customer has closed the eavrop
 *  
 */
public enum EavropStateType {
	UNASSIGNED,  
	ASSIGNED, 
	ACCEPTED,
	ONGOING,
	ON_HOLD,
	SENT, 
	APPROVED,
	CLOSED;
	
	private static final EavropStateType[] COMPLETED = {
		SENT,
		APPROVED,
		CLOSED};

	
	public boolean isCompleted(){
		if(Arrays.asList(COMPLETED).contains(this)){
			return true;
		}
		return false;
	}
}
