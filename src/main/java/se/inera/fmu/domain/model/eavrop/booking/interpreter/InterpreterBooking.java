package se.inera.fmu.domain.model.eavrop.booking.interpreter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;

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
		this.interpreterBookingStatus = InterpreterBookingStatusType.BOOKED;
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
		return InterpreterBookingStatusType.NOT_PRESENT.equals(this.getInterpreterBookingStatus());
	}

	public EavropEventDTO getAsEavropEvent(Booking booking ) {
		if(booking != null){
			return (booking.getPerson()!=null)?
					new EavropEventDTO(EavropEventDTOType.INTERPRETER, booking.getStartDateTime(), this.interpreterBookingStatus.toString(), booking.getPerson().getName(), booking.getPerson().getRole(), booking.getPerson().getOrganisation(), booking.getPerson().getUnit()):
					new EavropEventDTO(EavropEventDTOType.INTERPRETER, booking.getStartDateTime(), this.interpreterBookingStatus.toString(), null, null, null, null);
			
		}
		return null;
	}	


}
