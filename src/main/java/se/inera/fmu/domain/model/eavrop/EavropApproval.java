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

import se.inera.fmu.domain.model.person.Person;

@Entity
@Table(name = "T_EAVROP_APPROVAL")
@ToString
public class EavropApproval {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EAVROP_APPROVAL_ID", updatable = false, nullable = false)
	private Long eavropApprovalId;
	 
	@NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "RESPONSE_DATE_TIME")
	private LocalDateTime approvalTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;

	EavropApproval(){
		//Needed by Hibernate
	}

	public EavropApproval(LocalDateTime approvalTimestamp, Person person) {
		super();
		this.setApprovalTimestamp(approvalTimestamp);
		this.setPerson(person);
	}

	public LocalDateTime getApprovalTimestamp() {
		return approvalTimestamp;
	}

	private void setApprovalTimestamp(LocalDateTime approvalTimestamp) {
		this.approvalTimestamp = approvalTimestamp;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}
	
	//TODO embedded or own entity, value object
}
