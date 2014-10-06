package se.inera.fmu.domain.model.eavrop;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.shared.DomainEvent;

public abstract class EavropEvent implements DomainEvent<EavropEvent>{
	private final LocalDateTime eventDateTime = LocalDateTime.now();
	private final ArendeId arendeId;
	
	//~ Constructors ===================================================================================================
	public EavropEvent(final ArendeId arendeId) {
		Validate.notNull(arendeId);
    	this.arendeId = arendeId;
	}
	
	//~ Property Methods ===============================================================================================

	public ArendeId getArendeId() {
		return this.arendeId;
	}
	
	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}

	//~ Other Methods ==================================================================================================
	
	@Override
	public boolean sameEventAs(final EavropEvent other) {
		return other != null 
				&& this.getArendeId().equals(other.getArendeId())
				&& this.getEventDateTime().equals(other.getEventDateTime());
	}

	/**
	 * @param object to compare
	 * @return True if they have the same identity
	 * @see #sameIdentityAs(EavropEvent)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final EavropEvent other = (EavropEvent) object;
		return sameEventAs(other);
	}

	/**
	 * @return HashCode.
	 */
	@Override
	public int hashCode() {
		int result = getArendeId().hashCode();
		result = 31 * result + getEventDateTime().hashCode();
		return getArendeId().hashCode() + getEventDateTime().hashCode();
	}
}
