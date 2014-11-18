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

@Entity
@Table(name = "T_EAVROP_APPROVAL")
@ToString
public class EavropApproval implements ValueObject<EavropApproval>{

	//~ Instance fields ================================================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EAVROP_APPROVAL_ID", updatable = false, nullable = false)
	private Long eavropApprovalId;
	 
	@NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "APPROVAL_DATE_TIME")
	private DateTime approvalTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
    @OneToOne(cascade = CascadeType.ALL)
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
	
	public EavropEventDTO getAsEavropEvent() {
		String comment = (this.note!=null)?this.note.getText():null;
		
		return (this.getPerson()!=null)?
			new EavropEventDTO(EavropEventDTOType.EAVROP_APPROVED,this.getApprovalTimestamp(),null, comment, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit()):
			new EavropEventDTO(EavropEventDTOType.EAVROP_APPROVED,this.getApprovalTimestamp(),null, comment, null, null, null, null);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
