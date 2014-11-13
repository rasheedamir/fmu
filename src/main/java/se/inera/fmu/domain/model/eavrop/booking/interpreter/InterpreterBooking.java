package se.inera.fmu.domain.model.eavrop.booking.interpreter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import se.inera.fmu.domain.model.eavrop.InterpreterBookingEventDTO;
import se.inera.fmu.domain.model.eavrop.note.Note;

@Embeddable
public class InterpreterBooking {
    
	@Column(name = "INTERPRETER_STATUS_TYPE", nullable = true, updatable = true)
    @Enumerated(EnumType.STRING)
    @NotNull
	private InterpreterBookingStatusType interpreterBookingStatus;
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="INTERPRETER_DEVIATION_NOTE_ID", nullable = true, updatable = true)
	private Note deviationNote;

	public InterpreterBooking() {
		this.interpreterBookingStatus = InterpreterBookingStatusType.INTERPPRETER_BOOKED;
	}

	public InterpreterBookingStatusType getInterpreterBookingStatus() {
		return interpreterBookingStatus;
	}

	public void setInterpreterBookingStatus(
			InterpreterBookingStatusType interpreterBookingStatus) {
		this.interpreterBookingStatus = interpreterBookingStatus;
	}

	public Note getDeviationNote() {
		return deviationNote;
	}

	public void setDeviationNote(Note deviationNote) {
		this.deviationNote = deviationNote;
	}
	
	public boolean hasDeviation(){
		return InterpreterBookingStatusType.INTERPPRETER_NOT_PRESENT.equals(this.getInterpreterBookingStatus());
	}

	public InterpreterBookingEventDTO getAsInterpreterBookingEventDTO() {
		String comment = (this.deviationNote!=null)?this.deviationNote.getText():null;
		InterpreterBookingEventDTO interpreterBookingEventDTO = new InterpreterBookingEventDTO(getInterpreterBookingStatus(), comment);
		return interpreterBookingEventDTO;
	}	


}
