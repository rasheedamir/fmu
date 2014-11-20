package se.inera.fmu.domain.model.eavrop;

import java.util.Arrays;

import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;


/**
 * Created by Rickard on 9/12/14.
 */
public enum EavropStateType {
	UNASSIGNED,
	ASSIGNED,
	ACCEPTED,
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
