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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
import lombok.ToString;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="INFO_TYPE")
@Table(name = "T_INTYG_INFORMATION")
@ToString
public abstract class IntygInformation{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTYG_INFORMATION_ID", updatable = false, nullable = false)
	private Long id;

	@NotNull
    @Column(name = "INFO_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime informationTimestamp;
	
	@NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PARTY_ID")
	private Party party;
	
	public IntygInformation(LocalDateTime informationTimestamp, Party party) {
		super();
		this.informationTimestamp = informationTimestamp;
		this.party = party;
	}

	public LocalDateTime getIntformationTimestamp() {
		return informationTimestamp;
	}

	private void setIntformationTimestamp(LocalDateTime intformationTimestamp) {
		this.informationTimestamp = intformationTimestamp;
	}

	public Party getParty() {
		return party;
	}

	private void setParty(Party party) {
		this.party = party;
	}
	
}
