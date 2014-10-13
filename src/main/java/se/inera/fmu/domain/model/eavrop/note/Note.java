package se.inera.fmu.domain.model.eavrop.note;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.ValueObject;

@Entity
@Table(name = "T_NOTE")
@ToString
public class Note extends AbstractBaseEntity implements ValueObject<Note>,
		Serializable {
	// ~ Instance fields ================================================================================================

	// database primary key
	@Id
	@Column(name = "NOTE_ID", updatable = false, nullable = false)
	private String id;

	@Column(name = "TEXT")
	private String text;

	// TODO: Maybe add person if not reachable from audit created by and also if
	// notes from bestallare should be represented with this entity
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
	// ~ Constructors ===================================================================================================

	Note() {
		// Needed by Hibernate
	}

	public Note(String text, Person person) {
		this.id = UUID.randomUUID().toString();
		setText(text);
		setPerson(person);
	}

	// ~ Property Methods ===============================================================================================

	private void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	private void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return this.person;
	}

	// ~ Other Methods ==================================================================================================

	/**
	 * @param object to compare
	 * @return True if they have the same value
	 * @see #sameValueAs(Note)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Note other = (Note) object;
		return sameValueAs(other);
	}

	/**
	 * @return Hash code of id.
	 */
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean sameValueAs(Note other) {
		return other != null && this.id.equals(other.id);
	}
}
