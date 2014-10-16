package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;


public class OnHoldEavropStateTest extends AbstractNoteableEavropStateTest{

	@Test
	@Override
	public void testAddBookingDeviationResponse() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		assertNotNull(eavrop.getBookings());
		
		Booking booking = eavrop.getBookings().iterator().next();
		
		eavrop.addBookingDeviationResponse(booking.getBookingId(), createBookingDevationResponse());
		
		assertNotNull(eavrop.getBookings());
		assertEquals(1, eavrop.getBookings().size());
		assertNotNull(eavrop.getBookings().iterator().next().getBookingDeviation());
		assertNotNull(eavrop.getBookings().iterator().next().getBookingDeviation().getBookingDeviationResponse());
		assertEquals(BookingDeviationResponseType.RESTART, eavrop.getBookings().iterator().next().getBookingDeviation().getBookingDeviationResponse().getResponseType());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	
	@Override
	Eavrop getEavrop() {
		return createOnHoldEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		return EavropStateType.ON_HOLD;
	}
}
