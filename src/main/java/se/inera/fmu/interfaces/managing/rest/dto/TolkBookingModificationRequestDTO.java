package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;

@Getter
public class TolkBookingModificationRequestDTO {
	private String eavropId;
	private String bookingId;
	private InterpreterBookingStatusType bookingStatus;
	private String comment;

	public TolkBookingModificationRequestDTO setBookingId(String bookingId) {
		this.bookingId = bookingId;
		return this;
	}

	public TolkBookingModificationRequestDTO setEavropId(String eavropId) {
		this.eavropId = eavropId;
		return this;
	}

	public TolkBookingModificationRequestDTO setBookingStatus(InterpreterBookingStatusType bookingStatus) {
		this.bookingStatus = bookingStatus;
		return this;
	}

	public TolkBookingModificationRequestDTO setComment(String comment) {
		this.comment = comment;
		return this;
	}
}
