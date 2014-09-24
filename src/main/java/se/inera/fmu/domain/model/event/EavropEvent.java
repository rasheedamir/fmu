package se.inera.fmu.domain.model.event;

import java.util.UUID;

import lombok.ToString;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.DomainEvent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Rasheed on 7/7/14.
 */
@Entity
@Table(name = "T_EAVROP_EVENT")
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_discriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class EavropEvent extends AbstractBaseEntity implements DomainEvent<EavropEvent> {
	// TODO: Should probably not be an AuditingEntity, explicity set event datetime and user?

	// NewTimeBookedEvent **
	// EmailNewTimeBookedListener
	// NotifyFKForNewTimeBookedListener
	// [10:43:25] Rasheed: -- Event Store ??
	// [10:44:05] Rasheed: -- Event Bus ??
	// EavropAssignedToCareGiverEvent
	// CareGiverAcceptedEavropEvent
	// CareGiverRejectedEavropEvent
	// NewEavropCreatedEvent
	// IntygSignedEvent
	// ComplementsRequestedEvent
	// IntygApprovedEvent
	// NewDocumentRequestedEvent

	// ~ Instance fields
	// ================================================================================================

	// database primary key
	@Id
	@Column(name = "ID", updatable = false, nullable = false)
	private String eventId;

	@NotNull
	@Column(name = "EVENT_DATETIME")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eavropEventDateTime;

	// Is this bidirectional relationship neccessary?, probably not
//	@ManyToOne
//	private Eavrop eavrop;

	// ~ Constructors
	// ===================================================================================================

	EavropEvent() {
		// Needed by hibernate
	}

//	EavropEvent(final Eavrop evarop, final LocalDateTime eavropEventDateTime) {
//
//		this.setEventId(UUID.randomUUID().toString());
//		Validate.notNull(eavrop);
//		setEavrop(eavrop);
//		Validate.notNull(eavropEventDateTime);
//		setEavropEventDateTime(eavropEventDateTime);
//
//	}
	
	EavropEvent(final LocalDateTime eavropEventDateTime) {
		this.setEventId(UUID.randomUUID().toString());
		Validate.notNull(eavropEventDateTime);
		setEavropEventDateTime(eavropEventDateTime);
	}


	// ~ Property Methods
	// ===============================================================================================

	public String getEventId() {
		return eventId;
	}

	private void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getEavropEventDateTime() {
		return eavropEventDateTime;
	}

	private void setEavropEventDateTime(LocalDateTime eavropEventDateTime) {
		this.eavropEventDateTime = eavropEventDateTime;
	}

//	public Eavrop getEavrop() {
//		return eavrop;
//	}
//
//	private void setEavrop(Eavrop eavrop) {
//		this.eavrop = eavrop;
//	}

	// //~ Other Methods
	// ==================================================================================================
	//
	@Override
	public boolean sameEventAs(final EavropEvent other) {
		return other != null 
				&& this.getEventId().equals(other.getEventId());
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
		return getEventId().hashCode();
	}

	// CQRS

	// Controllers
	// Services
	// DomainM

}
