package se.inera.fmu.domain.model.eavrop;


public class EavropDeviationEventDTO implements Comparable<EavropDeviationEventDTO>{ 

	private final EavropDeviationEventDTOType deviationType;
	
    private final long eventTime;

    EavropDeviationEventDTO(EavropDeviationEventDTOType deviationType, long eventTime){
    	this.deviationType = deviationType;
    	this.eventTime = eventTime;
    }

    
	private long getEventTime() {
		return eventTime;
	}


	public EavropDeviationEventDTOType getDeviationType() {
		return deviationType;
	}


	@Override
	public int compareTo(EavropDeviationEventDTO o) {
		return Long.compare(getEventTime(), o.getEventTime());
	}
}
