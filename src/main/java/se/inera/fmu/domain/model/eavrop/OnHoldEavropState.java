package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.ApprovedEavropState;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

/**
 * The On hold state means that a serious deviation on a booking has 
 * occured, the deviation has been published to the bestallare/orderer 
 * and the and published deviation has a flag that says that they need
 * to reply if the utredning should continue/restart or 
 * be stopped.  
 * Depending on the responde its decided if the Eavrop should go back 
 * to Accepted state i.e. restarted or if it should go to Approved state
 * i.e. stopped 
 */
public class OnHoldEavropState extends AbstractNoteableEavropState{

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.ON_HOLD;
	}

	@Override
	public void addBookingDeviationResponse(Eavrop eavrop, BookingId bookingId, BookingDeviationResponse bookingDeviationResponse){
		Booking booking = eavrop.getBooking(bookingId);
		
		if(booking==null){
			throw new IllegalArgumentException("Booking with id:" + bookingId.getId() + " is not present on Eavrop with ArendeId: " + eavrop.getArendeId().toString());
		}
		
		if( ! booking.getBookingStatus().isCancelled()){
			//TODO: create separate state machine for bookings
			throw new IllegalArgumentException("Booking with id:" + bookingId.getId() + " on eavrop with arendeId: " + eavrop.getArendeId().toString()+" does not have a cancelled status: " + booking.getBookingStatus());
		}
		booking.setBookingDeviationResponse(bookingDeviationResponse);
		
		BookingDeviationResponseType responseType = bookingDeviationResponse.getResponseType();
		
		if(BookingDeviationResponseType.RESTART.equals(responseType)){
			//TODO: SET new base date, use some kind of domain service
			eavrop.setStartDate(bookingDeviationResponse.getResponseTimestamp().toLocalDate());
			eavrop.setEavropState(new AcceptedEavropState());
		
		}else if(BookingDeviationResponseType.STOP.equals(responseType)) {
			eavrop.setEavropState(new ApprovedEavropState());
		}
	}
}

