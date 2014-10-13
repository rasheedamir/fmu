package se.inera.fmu.domain.model.eavrop.booking;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Embeddable
@ToString
public class BookingDeviationResponse {
	
	@Column(name = "DEVIATION_RESPONSE_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingDeviationResponseType responseType;
    
    @NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "RESPONSE_DATE_TIME")
	private LocalDateTime responseTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="RESPONSE_NOTE_ID", nullable = true)
	private Note deviationResponseNote;

    
    public BookingDeviationResponse(){
    	//Needed by Hibernate
    }

	public BookingDeviationResponse(BookingDeviationResponseType responseType, LocalDateTime responseTimestamp, Person person) {
		super();
		Validate.notNull(responseType);
		Validate.notNull(responseTimestamp);
		this.responseType = responseType;
		this.responseTimestamp = responseTimestamp;
		this.person = person;
	}
	
	public BookingDeviationResponseType getResponseType() {
		return responseType;
	}


	private void setResponseType(BookingDeviationResponseType responseType) {
		this.responseType = responseType;
	}


	public LocalDateTime getResponseTimestamp() {
		return responseTimestamp;
	}


	private void setResponseTimestamp(LocalDateTime responseTimestamp) {
		this.responseTimestamp = responseTimestamp;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}

	public Note getDeviationResponseNote() {
		return deviationResponseNote;
	}

	public void setDeviationResponseNote(Note deviationResponseNote) {
		this.deviationResponseNote = deviationResponseNote;
	}
	
}
