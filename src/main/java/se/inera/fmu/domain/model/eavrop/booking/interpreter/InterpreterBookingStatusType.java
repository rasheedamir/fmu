package se.inera.fmu.domain.model.eavrop.booking.interpreter;

/**
 * This Enum defines the statuses that an interpreter booking can have. 
 * INTERPRETER_BOOKED and INTERPRETATION_PERFORMED, INTERPRETER_CANCELED, 
 * INTERPRETER_PRESENT_BUT_NOT_USED represents normal statuses. 
 * The INTERPRETER_NOT_PRESENT represents a deviant status.      
 */
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
