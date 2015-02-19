package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;

/**
 * Enum describing possible DeviationTypes for events, and functionality to convert from BookingStatusType  
 *
 */
public enum EavropDeviationEventDTOType {
	CANCELLED_NOT_PRESENT,
	CANCELLED_BY_CAREGIVER,
	CANCELLED_LT_48_H,
	CANCELLED_GT_48_H,
	CANCELLED_LT_96_H,
	CANCELLED_GT_96_H,
	INTERPRETER_NOT_PRESENT,
	INTYG_COMPLEMENT_RESPONSE_DEVIATION,
	EAVROP_ASSIGNMENT_ACCEPT_DEVIATION,
	EAVROP_ASSESSMENT_LENGHT_DEVIATION,
	UNKNOWN;
	
	public static EavropDeviationEventDTOType convertToEavropDeviationEventDTOType(BookingStatusType deviantBookingStatusType){
		
		switch(deviantBookingStatusType){
		case CANCELLED_NOT_PRESENT :
			return EavropDeviationEventDTOType.CANCELLED_NOT_PRESENT;
		case CANCELLED_BY_CARE_GIVER:
			return EavropDeviationEventDTOType.CANCELLED_BY_CAREGIVER;
		case CANCELLED_LT_48_H:
			return EavropDeviationEventDTOType.CANCELLED_LT_48_H;
		case CANCELLED_GT_48_H:
			return EavropDeviationEventDTOType.CANCELLED_GT_48_H;
		case CANCELLED_LT_96_H:
			return EavropDeviationEventDTOType.CANCELLED_LT_96_H;
		case CANCELLED_GT_96_H:
			return EavropDeviationEventDTOType.CANCELLED_GT_96_H;
		default:
			return EavropDeviationEventDTOType.UNKNOWN;
		}
	}
}
