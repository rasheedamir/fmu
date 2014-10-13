package se.inera.fmu.domain.model.eavrop;

import java.util.ArrayList;
import java.util.List;

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
	
	private static final List<EavropStateType> NOT_ACCEPTED;
	
	static {
		NOT_ACCEPTED = new ArrayList<EavropStateType>();
		NOT_ACCEPTED.add(UNASSIGNED);
		NOT_ACCEPTED.add(ASSIGNED);
	}
	
	public static List<EavropStateType> notAcceptedStatuses(){
		return NOT_ACCEPTED;
	}
	
}
