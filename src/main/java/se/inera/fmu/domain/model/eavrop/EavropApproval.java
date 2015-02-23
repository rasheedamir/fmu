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
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.ValueObject;
/**
 * The Approved state is achieved when the customer communicates that the eavrop have been approved 
 * This Object represents the approval, who has done the approval and optionally a note regarding the approval 
 */
@Entity
@Table(name = "T_EAVROP_APPROVAL")
@ToString
public class EavropApproval implements ValueObject<EavropApproval>{

	private static final long serialVersionUID = 1L;

	//~ Instance fields ================================================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EAVROP_APPROVAL_ID", updatable = false, nullable = false)
	private Long eavropApprovalId;
	 
	@NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "APPROVAL_DATE_TIME")
	private DateTime approvalTimestamp;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="NOTE_ID", nullable = true)
	private Note note;
    
    //~ Constructors ===================================================================================================
    
	EavropApproval(){
		//Needed by Hibernate
	}

	public EavropApproval(DateTime approvalTimestamp, Person person) {
		super();
		this.setApprovalTimestamp(approvalTimestamp);
		this.setPerson(person);
	}

	public EavropApproval(DateTime approvalTimestamp, Person person, Note note) {
		this(approvalTimestamp, person);
		this.setNote(note);
	}
	
	//~ Property Methods ===============================================================================================
	
	public DateTime getApprovalTimestamp() {
		return approvalTimestamp;
	}

	private void setApprovalTimestamp(DateTime approvalTimestamp) {
		this.approvalTimestamp = approvalTimestamp;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}
	
	public Note getNote() {
		return note;
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

        return sameValueAs((EavropApproval) o);
    }

	@Override
    public boolean sameValueAs(EavropApproval other) {
        return other != null && approvalTimestamp.equals(other.approvalTimestamp);
    }

    @Override
    public int hashCode() {
        return this.approvalTimestamp.hashCode();
    }
}
