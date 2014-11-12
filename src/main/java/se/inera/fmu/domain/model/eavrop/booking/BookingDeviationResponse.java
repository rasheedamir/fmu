package se.inera.fmu.domain.model.eavrop.booking;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;

@Embeddable
@ToString
public class BookingDeviationResponse {
	
	@Column(name = "DEVIATION_RESPONSE_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingDeviationResponseType responseType;
    
    @NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "DEVIATION_RESPONSE_DATE_TIME")
	private DateTime responseTimestamp;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DEVIATION_RESPONSE_PERSON_ID")
	private Person person;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DEVIATION_RESPONSE_NOTE_ID", nullable = true)
	private Note deviationResponseNote;

    
    public BookingDeviationResponse(){
    	//Needed by Hibernate
    }
    
    public BookingDeviationResponse(BookingDeviationResponseType responseType, DateTime responseTimestamp, Person person) {
    	this(responseType, responseTimestamp, person, null);
    }
    
	public BookingDeviationResponse(BookingDeviationResponseType responseType, DateTime responseTimestamp, Person person, Note note) {
		super();
		Validate.notNull(responseType);
		Validate.notNull(responseTimestamp);
		this.responseType = responseType;
		this.responseTimestamp = responseTimestamp;
		this.person = person;
		this.deviationResponseNote = note;
	}
	
	public BookingDeviationResponseType getResponseType() {
		return responseType;
	}


	private void setResponseType(BookingDeviationResponseType responseType) {
		this.responseType = responseType;
	}


	public DateTime getResponseTimestamp() {
		return responseTimestamp;
	}


	private void setResponseTimestamp(DateTime responseTimestamp) {
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

	public EavropEventDTO getAsEavropEvent() {
		EavropEventDTOType type = (BookingDeviationResponseType.STOP.equals(getResponseType()))?EavropEventDTOType.BOOKING_DEVIATION_RESPONSE_STOP:EavropEventDTOType.BOOKING_DEVIATION_RESPONSE_RESTART;
		String comment = (getDeviationResponseNote()!=null)?getDeviationResponseNote().getText():null;
		
		return (this.getPerson()!=null)?
			new EavropEventDTO(type, this.responseTimestamp, null, comment, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit()):
			new EavropEventDTO(type, this.responseTimestamp, null, comment, null, null, null, null);
	}
	
}
