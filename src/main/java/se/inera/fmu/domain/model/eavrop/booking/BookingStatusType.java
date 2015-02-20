package se.inera.fmu.domain.model.eavrop.booking;

import java.util.Arrays;
import java.util.List;

import se.inera.fmu.domain.model.eavrop.UtredningType;

/**
 * This Enum defines the statuses that a  Booking can have.
 * The statuses are also dependent on BookingType and UtredningType.
 * Booked and Performed represents the normal statuses, the other ones represents deviant statuses.     
 */
public enum BookingStatusType {
	BOOKED,
	PERFORMED,
	CANCELLED_NOT_PRESENT,
	CANCELLED_BY_CARE_GIVER,
	CANCELLED_LT_48_H,
	CANCELLED_GT_48_H,
	CANCELLED_LT_96_H,
	CANCELLED_GT_96_H;
	
	
	private static final BookingStatusType[] DEVIANT = {
		CANCELLED_NOT_PRESENT,
		CANCELLED_BY_CARE_GIVER,
		CANCELLED_LT_48_H,
		CANCELLED_GT_48_H,
		CANCELLED_LT_96_H,
		CANCELLED_GT_96_H};
	
	
	private static final BookingStatusType[] TMU_SLU_EXAMINIATION_STATUSES = {
		BOOKED,
		PERFORMED,
		CANCELLED_NOT_PRESENT,
		CANCELLED_BY_CARE_GIVER,
		CANCELLED_LT_48_H,
		CANCELLED_GT_48_H};
	
	private static final BookingStatusType[] AFU_EXAMINIATION_STATUSES = {
		BOOKED,
		PERFORMED,
		CANCELLED_NOT_PRESENT,
		CANCELLED_BY_CARE_GIVER,
		CANCELLED_LT_96_H,
		CANCELLED_GT_96_H};

	private static final BookingStatusType[] BREFING_STATUSES = {
		BOOKED,
		PERFORMED,
		CANCELLED_NOT_PRESENT,
		CANCELLED_BY_CARE_GIVER};

	private static final BookingStatusType[] INTERNAL_WORK_STATUSES = {
		BOOKED,
		PERFORMED,
		CANCELLED_BY_CARE_GIVER};
	

	
	public boolean isBooked(){
		if(BOOKED.equals(this)){
			return true;
		}
		return false;
	}

	public boolean isPerformed(){
		if(PERFORMED.equals(this)){
			return true;
		}
		return false;
	}
	
	public boolean isDeviant(){
		if(Arrays.asList(DEVIANT).contains(this)){
			return true;
		}
		return false;
	}
	
	
	public static List<BookingStatusType> getValidBookingStatuses(UtredningType utredningType, BookingType bookingType){
		switch(bookingType){
		case EXAMINATION :
			return getValidExaminationStatuses(utredningType);
		case BREIFING_WITH_CITIZEN:
			return Arrays.asList(BREFING_STATUSES);
		case INTERNAL_WORK:
			return Arrays.asList(INTERNAL_WORK_STATUSES);
		default:
			 throw new IllegalArgumentException("Unknown value: " + bookingType.name());
		}	
	}
	
	private static List<BookingStatusType> getValidExaminationStatuses(UtredningType utredningType){
		switch(utredningType){
		case TMU :
			return Arrays.asList(TMU_SLU_EXAMINIATION_STATUSES);
		case SLU:
			return Arrays.asList(TMU_SLU_EXAMINIATION_STATUSES);
		case AFU:
			return Arrays.asList(AFU_EXAMINIATION_STATUSES);
		default:
			 throw new IllegalArgumentException("Unknown value: " + utredningType.name());
		}	
	}

}
