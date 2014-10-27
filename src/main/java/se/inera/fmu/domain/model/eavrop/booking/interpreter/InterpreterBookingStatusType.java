package se.inera.fmu.domain.model.eavrop.booking.interpreter;

public enum InterpreterBookingStatusType {
	BOOKED,
	PERFORMED,
	CANCELED,
	NOT_PRESENT,
	PRESENT_BUT_NOT_USED;
	
	public boolean isDeviant(){
		return NOT_PRESENT.equals(this);	
	}
}
