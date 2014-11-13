package se.inera.fmu.domain.model.eavrop.booking.interpreter;

public enum InterpreterBookingStatusType {
	INTERPPRETER_BOOKED,
	INTERPRETATION_PERFORMED,
	INTERPPRETER_CANCELED,
	INTERPPRETER_NOT_PRESENT,
	INTERPPRETER_PRESENT_BUT_NOT_USED;
	
	public boolean isDeviant(){
		return INTERPPRETER_NOT_PRESENT.equals(this);	
	}
}
