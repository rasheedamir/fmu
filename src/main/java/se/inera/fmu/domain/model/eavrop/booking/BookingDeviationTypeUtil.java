package se.inera.fmu.domain.model.eavrop.booking;

import java.util.Arrays;

import se.inera.fmu.domain.model.eavrop.UtredningType;


//TODO: Move to a domain service
public class BookingDeviationTypeUtil {
	
	private static final BookingDeviationType[] SLU_ON_HOLD_DEVIATIONS = {
		BookingDeviationType.INVANARE_CANCELLED_LT_96, 
		BookingDeviationType.INVANARE_CANCELLED_LT_48
		};

	private static final BookingDeviationType[] DEFAULT_ON_HOLD_DEVIATIONS = {
		BookingDeviationType.INVANARE_CANCELLED_LT_48
	};

	
	public static boolean isDeviationTypeReasonForOnHold(BookingDeviationType deviationType, UtredningType utredningType ){
		if(UtredningType.SLU.equals(utredningType )){
			return  Arrays.asList(SLU_ON_HOLD_DEVIATIONS).contains(deviationType);
		}else{
			return Arrays.asList(DEFAULT_ON_HOLD_DEVIATIONS).contains(deviationType);
		}
	}
	
	
}
