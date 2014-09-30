package se.inera.fmu.domain.model.eavrop;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.shared.DomainEvent;

public abstract class EavropEvent implements DomainEvent<EavropEvent>{
	private final String id;
	private final LocalDateTime eventDateTime = LocalDateTime.now();
	private final ArendeId arendeId;
	
	//~ Constructors ===================================================================================================
	public EavropEvent(final ArendeId arendeId) {
		this.id = UUID.randomUUID().toString();
		Validate.notNull(arendeId);
    	this.arendeId = arendeId;
	}
	
	//~ Property Methods ===============================================================================================

	public String getId() {
		return this.id;
	}

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
				&& this.getId().equals(other.getId());
	}

	/**
	 * @param object
	 *            to compare
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
	 * @return Hash code of tracking id.
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
