package se.inera.fmu.domain.model.eavrop.booking;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.party.Party;
import lombok.ToString;

@Entity
@Table(name = "T_DEVIATION_RESPONSE")
@ToString
public class BookingDeviationResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;

	@Column(name = "DEVIATION_RESPONSE_TYPE", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingDeviationResponseType responseType;
    
    @NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "RESPONSE_DATE_TIME")
	private LocalDateTime responseTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PARTY_ID")
	private Party party;

	public BookingDeviationResponse(BookingDeviationResponseType responseType, LocalDateTime responseTimestamp, Party party) {
		super();
		this.responseType = responseType;
		this.responseTimestamp = responseTimestamp;
		this.party = party;
	}
	
	

}
