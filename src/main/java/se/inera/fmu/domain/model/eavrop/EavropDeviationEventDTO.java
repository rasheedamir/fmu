package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;


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
	/**
	 * @param other to compare
	 * @return True if they have the same identity
	 */
	@Override
	public boolean equals(final Object other) {
		if (this == other){
			return true;
		}
		if (other == null || getClass() != other.getClass()){
			return false;
		}
		return sameEventAs((EavropDeviationEventDTO)other);
	}
	 
	private boolean sameEventAs(final EavropDeviationEventDTO other) {
		//TODO: Add some kind of unique id
		return other != null 
				&& this.getDeviationType().equals(other.getDeviationType())
				&& this.getEventTime()==other.getEventTime();
	}
	/**
	 * @return HashCode.
	 */
	@Override
	public int hashCode() {
		int result = getDeviationType().hashCode();
		result = result * new DateTime(this.getEventTime()).hashCode() ;
		return result;
	}
}
