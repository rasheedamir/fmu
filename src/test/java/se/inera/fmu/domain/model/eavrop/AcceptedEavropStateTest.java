package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;


public class AcceptedEavropStateTest extends AbstractNoteableEavropStateTest{

	@Test
	@Override
	public void testAddBooking() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		Booking booking = createBooking(); 
		eavrop.addBooking(booking);
		
		Set<Booking> bookings = eavrop.getBookings();
		assertNotNull(bookings);
		assertEquals(1, bookings.size()); 
		assertEquals(booking, eavrop.getBooking(booking.getBookingId()));
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}
	
	@Test
	@Override
	public void testCancelBooking() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		Booking booking = createBooking(); 
		eavrop.addBooking(booking);
		eavrop.setBookingStatus(booking.getBookingId(), BookingStatusType.CANCELLED_NOT_PRESENT, createNote());

		Set<Booking> bookings = eavrop.getBookings();
		assertNotNull(bookings);
		assertEquals(1, bookings.size()); 
		assertEquals(booking, eavrop.getBooking(booking.getBookingId()));
		assertEquals(Boolean.TRUE, eavrop.getBooking(booking.getBookingId()).getBookingStatus().isCancelled());		
		assertEquals(EavropStateType.ON_HOLD, eavrop.getEavropState().getEavropStateType());
	}
	

	
//	@Test
//	@Override
//	public void testSetDocumentsSentFromBestallare() {
//		Eavrop eavrop = getEavrop();
//		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
//		eavrop.setDateTimeDocumentsSentFromBestallare(new DateTime());
//		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
//	}
	
	@Test
	@Override
	public void testAddReceivedDocumentToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addReceivedDocument(createReceivedDocument());

		assertNotNull(eavrop.getReceivedDocuments());
		assertEquals(1, eavrop.getReceivedDocuments().size());
		assertNull(eavrop.getRequestedDocuments());

		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testAddRequestedDocumentToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addRequestedDocument(createRequestedDocument());

		assertNotNull(eavrop.getRequestedDocuments());
		assertEquals(1, eavrop.getRequestedDocuments().size());
		assertNull(eavrop.getReceivedDocuments());

		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testAddIntygSignedInformationToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygSignedInformation(createIntygSignedInformation());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}
	
	@Test
	@Override
	public void testAddIntygComplementRequestToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}
	
	@Test
	@Override
	public void testAddIntygApprovedToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygApprovedInformation(createIntygApprovedInformation());
		
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testApproveEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.approveEavrop(createEavropApproval());
		
		assertEquals(EavropStateType.APPROVED, eavrop.getEavropState().getEavropStateType());
	}
	
	@Override
	Eavrop getEavrop() {
		return createAcceptedEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		// TODO Auto-generated method stub
		return EavropStateType.ACCEPTED;
	}
}
