package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.person.Person;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="INFO_TYPE")
@Table(name = "T_INTYG_INFORMATION")
@ToString
public abstract class IntygInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTYG_INFORMATION_ID", updatable = false, nullable = false)
	private Long id;

	@NotNull
    @Column(name = "INFO_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime informationTimestamp;
	
	@NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
	public IntygInformation(){
        //Needed by hibernate
    }

	public IntygInformation(DateTime informationTimestamp, Person person) {
		super();
		this.informationTimestamp = informationTimestamp;
		this.person = person;
	}

	public DateTime getInformationTimestamp() {
		return informationTimestamp;
	}

	private void setIntformationTimestamp(DateTime intformationTimestamp) {
		this.informationTimestamp = intformationTimestamp;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}
	
	public abstract EavropEventDTO getAsEavropEvent();
	
}
