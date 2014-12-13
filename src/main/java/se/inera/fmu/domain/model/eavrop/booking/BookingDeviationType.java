package se.inera.fmu.domain.model.eavrop.booking;

import java.util.Arrays;

import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;

public enum BookingDeviationType {
	INVANARE_ABSENT,
	CANCELLED_BY_INVANARE_LT_48,
	CANCELLED_BY_INVANARE_GT_48,
	CANCELLED_BY_INVANARE_LT_96,
	CANCELLED_BY_INVANARE_GT_96,
	CANCELLED_BY_CAREGIVER,
	CANCELLED_BY_INTERPRETER,
	INTERPRETER_ABSENT,
	INTERPRETER_NOT_UTILIZED;
	
	public static BookingDeviationType convert(BookingStatusType bookingStatus){
		switch(bookingStatus){
		case CANCELLED_NOT_PRESENT :
			return  INVANARE_ABSENT;
		case CANCELLED_BY_CARE_GIVER:
			return CANCELLED_BY_CAREGIVER;
		case CANCELLED_LT_48_H:
			return CANCELLED_BY_INVANARE_LT_48;
		case CANCELLED_GT_48_H:
			return CANCELLED_BY_INVANARE_GT_48;
		case CANCELLED_LT_96_H:
			return CANCELLED_BY_INVANARE_LT_96;
		case CANCELLED_GT_96_H:
			return CANCELLED_BY_INVANARE_GT_96;
		default:
			 throw new IllegalArgumentException("Not a booking devation value: " +bookingStatus.name());
		}
	}
	
	public static BookingDeviationType convert(InterpreterBookingStatusType interpreterBookingStatus){
		switch(interpreterBookingStatus){
		case INTERPRETER_NOT_PRESENT :
			return INTERPRETER_ABSENT;
//		case INTERPRETER_CANCELED:
//			return CANCELLED_BY_INTERPRETER;
//		case INTERPRETER_PRESENT_BUT_NOT_USED:
//			return INTERPRETER_NOT_UTILIZED;
		default:
			 throw new IllegalArgumentException("Status is not a booking devation value: " +interpreterBookingStatus.name());
		}
	}
}
