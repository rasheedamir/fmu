package se.inera.fmu.domain.model.eavrop;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import se.inera.fmu.application.ApplicationEvents;
import se.inera.fmu.domain.shared.DomainEvent;

@ToString
public abstract class EavropEvent implements DomainEvent<EavropEvent>, ApplicationEvents{
	private final DateTime eventDateTime = DateTime.now();
	private final EavropId eavropId;
	
	//~ Constructors ===================================================================================================
	public EavropEvent(final EavropId eavropId) {
		Validate.notNull(eavropId);
    	this.eavropId= eavropId;
	}
	
	//~ Property Methods ===============================================================================================

	public EavropId getEavropId() {
		return this.eavropId;
	}
	
	public DateTime getEventDateTime() {
		return eventDateTime;
	}

	//~ Other Methods ==================================================================================================
	
	@Override
	public boolean sameEventAs(final EavropEvent other) {
		return other != null 
				&& this.getEavropId().equals(other.getEavropId())
				&& this.getEventDateTime().equals(other.getEventDateTime());
	}

	/**
	 * @param object to compare
	 * @return True if they have the same identity
	 * @see #sameIdentityAs(EavropEvent)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object){
			return true;
		}
		if (object == null || getClass() != object.getClass()){
			return false;
		}

		final EavropEvent other = (EavropEvent) object;
		return sameEventAs(other);
	}

	/**
	 * @return HashCode.
	 */
	@Override
	public int hashCode() {
		int result = getEavropId().hashCode();
		result = 31 * result + getEventDateTime().hashCode();
		return getEavropId().hashCode() + getEventDateTime().hashCode();
	}
}
