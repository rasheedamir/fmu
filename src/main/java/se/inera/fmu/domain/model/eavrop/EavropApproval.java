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

import se.inera.fmu.domain.party.Party;

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
    @JoinColumn(name="PARTY_ID")
	private Party party;

	public EavropApproval(LocalDateTime approvalTimestamp, Party party) {
		super();
		this.approvalTimestamp = approvalTimestamp;
		this.party = party;
	}
	
	//TODO embedded or own entity, value object
}
