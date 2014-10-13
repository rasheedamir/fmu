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
@Table(name = "T_EAVROP_COMP_APPROVAL")
@ToString
public class EavropCompensationApproval {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "RESPONSE_DATE_TIME")
	private LocalDateTime responseTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	public EavropCompensationApproval(){
		//needed by Hibernate
	}
	
	public EavropCompensationApproval(LocalDateTime responseTimestamp, Person person) {
		super();
		this.responseTimestamp = responseTimestamp;
		this.person = person;
	}
	
	//TODO: embed in eavrop?

}
