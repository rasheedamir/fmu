package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.interfaces.managing.dtomapper.UtredningDTOMapper.NoneBookingEvents;

@Getter
@SuppressWarnings("rawtypes")
public class HandelseDTO {
	private Enum handelse;
	private StatusDTO tolkStatus;
	private StatusDTO handelseStatus;
	private Long dateOfEvent;
	private TimeDTO timeOfEvent;
	private String utredaPerson;
	private String role;

	public HandelseDTO setHandelse(Enum bookingType) {
		if (isValidEventType(bookingType))
			this.handelse = bookingType;
		return this;
	}

	private boolean isValidEventType(Enum bookingType) {
		return bookingType instanceof BookingType
				|| bookingType instanceof NoneBookingEvents;
	}
	
	public HandelseDTO setTolkStatus(StatusDTO tolkStatus) {
		this.tolkStatus = tolkStatus;
		return this;
	}
	
	public HandelseDTO setHandelseStatus(StatusDTO handelseStatus) {
		this.handelseStatus = handelseStatus;
		return this;
	}
	
	public HandelseDTO setDateOfEvent(Long dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
		return this;
	}
	
	public HandelseDTO setTimeOfEvent(DateTime dateTime) {
		if(dateTime == null)
			return this;
		
		if(this.timeOfEvent == null)
			this.timeOfEvent = new TimeDTO();
		
		this.timeOfEvent.setHour(dateTime.getHourOfDay())
		.setMinute(dateTime.getMinuteOfHour());
		
		return this;
	}
	
	public HandelseDTO setUtredaPerson(String utredaPerson) {
		this.utredaPerson = utredaPerson;
		return this;
	}
	
	public HandelseDTO setRole(String role) {
		this.role = role;
		return this;
	}
}
