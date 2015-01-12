package se.inera.fmu.domain.model.eavrop;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import org.joda.time.DateTime;

import se.inera.fmu.domain.converter.BooleanToStringConverter;
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
	@Column(name = "APPROVED", columnDefinition="char(1)")
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean approved;
	
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "COMPENSATION_DATE_TIME")
	private DateTime compensationDateTime;

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
	
	public EavropCompensationApproval(Boolean approved, DateTime compensationDateTime, Person person) {
		super();
		this.setApproved(approved);
		this.setCompensationDateTime(compensationDateTime);
		this.setPerson(person);
	}

	public EavropCompensationApproval(Boolean approved, DateTime compensationDateTime, Person person, Note note) {
		this(approved, compensationDateTime, person);
		this.setNote(note);
	}
	
	//~ Property Methods ===============================================================================================

    public Boolean isApproved() {
		return approved;
	}

	private void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public DateTime getCompensationDateTime() {
		return compensationDateTime;
	}

	private void setCompensationDateTime(DateTime compensationDateTime) {
		this.compensationDateTime = compensationDateTime;
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
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }

        return sameValueAs((EavropCompensationApproval) o);
    }

	@Override
    public boolean sameValueAs(EavropCompensationApproval other) {
        return other != null && this.approved == other.approved && compensationDateTime.equals(other.compensationDateTime);
    }

    @Override
    public int hashCode() {
        return this.compensationDateTime.hashCode();
    }
	
	//TODO: embed in eavrop?
}
