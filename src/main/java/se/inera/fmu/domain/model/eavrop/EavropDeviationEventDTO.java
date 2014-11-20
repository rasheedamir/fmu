package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;

public class EavropDeviationEventDTO implements Comparable<EavropDeviationEventDTO>{ 

	private final EavropDeviationEventDTOType deviationType;
	
    private final DateTime eventTime;

    EavropDeviationEventDTO(EavropDeviationEventDTOType deviationType, DateTime eventTime){
    	this.deviationType = deviationType;
    	this.eventTime = eventTime;
    }

    
	public DateTime getEventTime() {
		return eventTime;
	}


	public EavropDeviationEventDTOType getDeviationType() {
		return deviationType;
	}


	@Override
	public int compareTo(EavropDeviationEventDTO o) {
		return getEventTime().compareTo(o.getEventTime());
	}
}
