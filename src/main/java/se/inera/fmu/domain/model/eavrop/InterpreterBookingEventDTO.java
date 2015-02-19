package se.inera.fmu.domain.model.eavrop;

import java.util.Arrays;
import java.util.List;

import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
/**
 * DTO for data about a Interpreter booking
 *
 */
public class InterpreterBookingEventDTO { 
	
    private final InterpreterBookingStatusType interpreterStatus;
    private final String interpreterComment;
    
	public InterpreterBookingEventDTO(InterpreterBookingStatusType interpreterStatus, String interpreterComment) {
		this.interpreterStatus = interpreterStatus;
		this.interpreterComment = interpreterComment;
	}

	public InterpreterBookingStatusType getInterpreterStatus() {
		return interpreterStatus;
	}
	
	public String getInterpreterComment(){
		return interpreterComment;
	}
	
	public List<InterpreterBookingStatusType> getValidInterpreterStatuses(){
		return Arrays.asList(InterpreterBookingStatusType.values()) ; 
	}
}
