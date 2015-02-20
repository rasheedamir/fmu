package se.inera.fmu.domain.model.eavrop.note;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.ValueObject;

/**
 * This class/entity represents a text note and who wrote it
 */
@Entity
@Table(name = "T_NOTE")
@ToString
public class Note extends AbstractBaseEntity implements ValueObject<Note>, Comparable<Note>, Serializable  {
	private static final long serialVersionUID = 1L;

	// ~ Instance fields ================================================================================================
	// database primary key
	@EmbeddedId
	private NoteId noteId;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", length = 40)
	private NoteType noteType;
	
	@Column(name = "TEXT", columnDefinition="TEXT")
	private String text;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
	// ~ Constructors ===================================================================================================

	Note() {
		// Needed by Hibernate
	}

	public Note(NoteType noteType, String text, Person person) {
		this.setNoteId(new NoteId(UUID.randomUUID().toString()));
		this.setNoteType(noteType);
		setText(text);
		setPerson(person);
	}

	// ~ Property Methods ===============================================================================================

	public NoteId getNoteId(){
		return this.noteId;
	}
	
	private void setNoteId(NoteId noteId){
		this.noteId = noteId;
	}

	public NoteType getNoteType() {
		return noteType;
	}

	private void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}
	
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
	 * Checks if the note is of type 'Eavrop' and have been created by the user with the specified HsaId and return true if that is so.
	 * @param hsaId 
	 * @return
	 */
	
	public boolean isRemovableBy(HsaId hsaId){
		if(NoteType.EAVROP.equals(getNoteType())){
			Person person = getPerson();
			if(getPerson()!=null){
				if(person instanceof HoSPerson){
					HoSPerson hosPerson = (HoSPerson)person;
					if(hosPerson.getHsaId()!=null){
						return hosPerson.getHsaId().equals(hsaId);
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * @param object to compare
	 * @return True if they have the same value
	 * @see #sameValueAs(Note)
	 */
	@Override
	public boolean equals(final Object object) {
		if (this == object){
			return true;
		}
		if (object == null || getClass() != object.getClass()){
			return false;
		}

		final Note other = (Note) object;
		return sameValueAs(other);
	}

	/**
	 * @return Hash code of id.
	 */
	@Override
	public int hashCode() {
		return this.getNoteId().hashCode();
	}

	@Override
	public boolean sameValueAs(Note other) {
		return other != null && this.getNoteId().equals(other.getNoteId());
	}

	@Override
	public int compareTo(Note other) {
	        return this.getCreatedDate().compareTo(other.getCreatedDate());
	}
}
