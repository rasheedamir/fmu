package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;


public class SentEavropStateTest extends AbstractNoteableEavropStateTest{

	@Test
	@Override
	public void testAddIntygSentInformationToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygSentInformation(createIntygSentInformation());
		
		assertEquals(EavropStateType.SENT, eavrop.getEavropState().getEavropStateType());
	}
	
	@Test
	@Override
	public void testAddIntygComplementRequestToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygComplementRequestInformation(createIntygComplementRequestInformation());
		
		assertEquals(EavropStateType.SENT, eavrop.getEavropState().getEavropStateType());
	}
	
	@Test
	@Override
	public void testAddIntygApprovedToEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.addIntygApprovedInformation(createIntygApprovedInformation());
		
		assertEquals(EavropStateType.SENT, eavrop.getEavropState().getEavropStateType());
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
		return createSentEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		return EavropStateType.SENT;
	}
}
