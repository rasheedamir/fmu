package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;

@Getter
public class BookingModificationRequestDTO {
	private String eavropId;
	private String bookingId;
	private BookingStatusType bookingStatus;
	private InterpreterBookingStatusType tolkStatus;
	private String comment;

	public BookingModificationRequestDTO setBookingId(String bookingId) {
		this.bookingId = bookingId;
		return this;
	}

	public BookingModificationRequestDTO setEavropId(String eavropId) {
		this.eavropId = eavropId;
		return this;
	}

	public BookingModificationRequestDTO setBookingStatus(BookingStatusType bookingStatus) {
		this.bookingStatus = bookingStatus;
		return this;
	}

	public BookingModificationRequestDTO setComment(String comment) {
		this.comment = comment;
		return this;
	}
}
