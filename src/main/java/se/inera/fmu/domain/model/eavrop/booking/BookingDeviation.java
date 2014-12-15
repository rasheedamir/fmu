package se.inera.fmu.domain.model.eavrop.booking;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.ToString;
import se.inera.fmu.domain.model.eavrop.note.Note;

@Embeddable
@ToString
public class BookingDeviation  {
	
	@Column(name = "DEVIATION_TYPE", nullable = true, updatable = true, length = 32)
    @Enumerated(EnumType.STRING)
    @NotNull
	private BookingDeviationType deviationType;
  
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="DEVIATION_NOTE_ID", nullable = true)
	private Note deviationNote;
    
    //TODO, should we have a person which defines who set the deviation?
    
    @Embedded
    private BookingDeviationResponse bookingDeviationResponse; 

    public BookingDeviation() {
        // Needed by Hibernate
    }
    
	public BookingDeviation(BookingDeviationType deviationType, Note deviationNote) {
		super();
		this.setDeviationType(deviationType);
		this.setDeviationNote(deviationNote);
	}

	public BookingDeviationType getDeviationType() {
		return deviationType;
	}

	private void setDeviationType(BookingDeviationType deviationType) {
		this.deviationType = deviationType;
	}

	public Note getDeviationNote() {
		return deviationNote;
	}

	private void setDeviationNote(Note deviationNote) {
		this.deviationNote = deviationNote;
	}

	public BookingDeviationResponse getBookingDeviationResponse() {
		return bookingDeviationResponse;
	}

	public void setBookingDeviationResponse(
			BookingDeviationResponse bookingDeviationResponse) {
		this.bookingDeviationResponse = bookingDeviationResponse;
	}
}
