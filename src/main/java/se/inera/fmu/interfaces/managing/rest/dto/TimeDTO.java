package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;

@Getter
public class TimeDTO {
	private Integer hour;
	private Integer minute;
	
	public TimeDTO setHour(Integer hour) {
		this.hour = hour;
		return this;
	}
	
	public TimeDTO setMinute(Integer minute) {
		this.minute = minute;
		return this;
	}
}
