package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.ArrayList;
import java.util.List;

import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;

@SuppressWarnings("rawtypes")
public class StatusDTO {
	private Enum currentStatus;
	private List<Status> statuses;
	private String comment;

	public String getComment() {
		return comment;
	}

	public Enum getCurrentStatus() {
		return currentStatus;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public StatusDTO setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public StatusDTO setCurrentStatus(Enum currentStatus) {
		if (isValidHandelseStatus(currentStatus))
			this.currentStatus = currentStatus;
		return this;
	}

	private boolean isValidHandelseStatus(Object status) {
		return status instanceof BookingStatusType
				|| status instanceof InterpreterBookingStatusType;
	}

	public StatusDTO setStatuses(Object[] objects) {
		List<Status> retval = new ArrayList<StatusDTO.Status>();
		for (Object status : objects) {
			if (isValidHandelseStatus(status)) {
				Status s = new Status();
				if (status instanceof BookingStatusType) {
					BookingStatusType bt = (BookingStatusType) status;
					s.setName(bt)
					.setRequireComment(bt.isCancelled());
				} else {
					InterpreterBookingStatusType it = (InterpreterBookingStatusType) status;
					s.setName(it)
					.setRequireComment(it.isDeviant());
				}
				retval.add(s);
			}
		}
		
		this.statuses = retval;
		return this;
	}

	protected class Status {
		private Enum name;
		private Boolean requireComment;

		public Enum getName() {
			return name;
		}

		public Boolean getRequireComment() {
			return requireComment;
		}

		public Status setName(Enum status) {
			if (isValidHandelseStatus(status))
				this.name = status;
			return this;
		}

		public Status setRequireComment(Boolean requireComment) {
			this.requireComment = requireComment;
			return this;
		}
	}
}
