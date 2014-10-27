package se.inera.fmu.domain.model.eavrop.booking;

import java.util.Arrays;

public enum BookingStatusType {
	BOOKED,
	PERFORMED,
	CANCELLED_NOT_PRESENT,
	CANCELLED_BY_CARE_GIVER,
	CANCELLED_LT_48_H,
	CANCELLED_GT_48_H,
	CANCELLED_LT_96_H,
	CANCELLED_GT_96_H;
	
	
	private static final BookingStatusType[] CANCELLED = {
		CANCELLED_NOT_PRESENT,
		CANCELLED_BY_CARE_GIVER,
		CANCELLED_LT_48_H,
		CANCELLED_GT_48_H,
		CANCELLED_LT_96_H,
		CANCELLED_GT_96_H};
	
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
	
	public boolean isCancelled(){
		if(Arrays.asList(CANCELLED).contains(this)){
			return true;
		}
		return false;
	}
}
