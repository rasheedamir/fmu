package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;


public class StatusDTO {
	private Enum currentStatus;
	private Status[] statuses;
	private String comment;
	
	public String getComment() {
		return comment;
	}
	
	public Enum getCurrentStatus() {
		return currentStatus;
	}
	
	public Status[] getStatuses() {
		return statuses;
	}
	
	public StatusDTO setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	public StatusDTO setCurrentStatus(Enum currentStatus) {
		if(isValidHandelseStatus(currentStatus))
		this.currentStatus = currentStatus;
		return this;
	}

	private boolean isValidHandelseStatus(Enum currentStatus) {
		return currentStatus instanceof BookingStatusType
				|| currentStatus instanceof InterpreterBookingStatusType;
	}
	
	public StatusDTO setStatuses(Enum[] statuses) {
		List<Status> retval = new ArrayList<StatusDTO.Status>();
		for (Enum status : statuses) {
			if(isValidHandelseStatus(status)){
				Status s = new Status();
				retval.add(s);
			}
		}
		return this;
	}
	
	protected class Status {
		private Enum name;
		private Boolean requireComment;
		
		public Status setName(Enum status) {
			if(isValidHandelseStatus(status))
			this.name = name;
			return this;
		}
		
		public Status setRequireComment(Boolean requireComment) {
			this.requireComment = requireComment;
			return this;
		}
	}
}
