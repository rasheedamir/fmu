package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;


public class OnHoldEavropStateTest extends AbstractEavropStateTest{

	@Test
	@Override
	public void testAddNoteToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addNote(createNote());
		
		assertNotNull(eavrop.getNotes());
		assertEquals(1, eavrop.getNotes().size()); 
		assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());
	}

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
