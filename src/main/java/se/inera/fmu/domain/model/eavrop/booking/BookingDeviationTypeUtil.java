package se.inera.fmu.domain.model.eavrop.booking;

import java.util.Arrays;

import se.inera.fmu.domain.model.eavrop.UtredningType;


//TODO: Move to a domain service
public class BookingDeviationTypeUtil {
	
	private static final BookingStatusType[] AFU_ON_HOLD_STATUSES = {
		BookingStatusType.CANCELLED_NOT_PRESENT, 
		BookingStatusType.CANCELLED_LT_96_H,
		BookingStatusType.CANCELLED_GT_96_H};

	private static final BookingStatusType[] DEFAULT_ON_HOLD_STATUSES = {
		BookingStatusType.CANCELLED_NOT_PRESENT,
		BookingStatusType.CANCELLED_LT_48_H,
		BookingStatusType.CANCELLED_GT_48_H};

	
	private static final BookingDeviationType[] DEVIATION_AFU_ON_HOLD_STATUSES = {
		BookingDeviationType.INVANARE_ABSENT, 
		BookingDeviationType.INVANARE_CANCELLED_LT_96,
		BookingDeviationType.INVANARE_CANCELLED_GT_96};

	private static final BookingDeviationType[] DEFAULT_DEVIATION_ON_HOLD_STATUSES = {
		BookingDeviationType.INVANARE_ABSENT,
		BookingDeviationType.INVANARE_CANCELLED_LT_48,
		BookingDeviationType.INVANARE_CANCELLED_GT_48};

	
	
	public static boolean isDeviationTypeReasonForOnHold(BookingDeviationType bookingDeviationType, UtredningType utredningType ){
		if(UtredningType.AFU.equals(utredningType )){
			return  Arrays.asList(DEVIATION_AFU_ON_HOLD_STATUSES).contains(bookingDeviationType);
		}else{
			return Arrays.asList(DEFAULT_DEVIATION_ON_HOLD_STATUSES).contains(bookingDeviationType);
		}
	}

	public static boolean isBookingStatusReasonForOnHold(BookingStatusType bookingStatus, UtredningType utredningType ){
		if(UtredningType.AFU.equals(utredningType )){
			return  Arrays.asList(AFU_ON_HOLD_STATUSES).contains(bookingStatus);
		}else{
			return Arrays.asList(DEFAULT_ON_HOLD_STATUSES).contains(bookingStatus);
		}
	}
	
	public static boolean isEavropBookingDeviation(BookingDeviationType bookingDeviationType, UtredningType utredningType ){
		if(UtredningType.AFU.equals(utredningType )){
			return  Arrays.asList(AFU_ON_HOLD_STATUSES).contains(bookingDeviationType);
		}else{
			return Arrays.asList(DEFAULT_ON_HOLD_STATUSES).contains(bookingDeviationType);
		}
	}
}
