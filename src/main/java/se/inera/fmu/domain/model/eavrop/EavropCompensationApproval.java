package se.inera.fmu.domain.model.eavrop;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.ValueObject;

@Entity
@Table(name = "T_EAVROP_COMP_APPROVAL")
@ToString
public class EavropCompensationApproval implements ValueObject<EavropCompensationApproval>{

	//~ Instance fields ================================================================================================
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "APPROVED")
	private boolean approved;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "COMPENSATION_DATE_TIME")
	private LocalDateTime compensationTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="NOTE_ID", nullable = true)
	private Note note;

    //~ Constructors ===================================================================================================

	public EavropCompensationApproval(){
		//needed by Hibernate
	}
	
	public EavropCompensationApproval(boolean approved, LocalDateTime compensationTimestamp, Person person) {
		super();
		this.setApproved(approved);
		this.setCompensationTimestamp(compensationTimestamp);
		this.setPerson(person);
	}

	public EavropCompensationApproval(boolean approved, LocalDateTime responseTimestamp, Person person, Note note) {
		this(approved, responseTimestamp, person);
		this.setNote(note);
	}

	//~ Property Methods ===============================================================================================

    public boolean isApproved() {
		return approved;
	}

	private void setApproved(boolean approved) {
		this.approved = approved;
	}

	public LocalDateTime getCompensationTimestamp() {
		return compensationTimestamp;
	}

	private void setCompensationTimestamp(LocalDateTime compensationTimestamp) {
		this.compensationTimestamp = compensationTimestamp;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}

	public Note getNote() {
		return this.note;
	}

	private void setNote(Note note) {
		this.note = note;
	}

    //~ Other Methods ==================================================================================================

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return sameValueAs((EavropCompensationApproval) o);
    }

	@Override
    public boolean sameValueAs(EavropCompensationApproval other) {
        return other != null && this.approved == other.approved && compensationTimestamp.equals(other.compensationTimestamp);
    }

    @Override
    public int hashCode() {
        return this.compensationTimestamp.hashCode();
    }
	
	//TODO: embed in eavrop?
}
