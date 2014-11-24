package se.inera.fmu.domain.model.eavrop.booking.interpreter;

public enum InterpreterBookingStatusType {
	INTERPRETER_BOOKED,
	INTERPRETATION_PERFORMED,
	INTERPRETER_CANCELED,
	INTERPRETER_NOT_PRESENT,
	INTERPRETER_PRESENT_BUT_NOT_USED;
	
	public boolean isDeviant(){
		return INTERPRETER_NOT_PRESENT.equals(this);	
	}
}
