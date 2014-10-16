package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;


public class ApprovedEavropStateTest extends AbstractNoteableEavropStateTest{


	@Test
	@Override
	public void testApproveEavropCompensation() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.approveEavropCompensation(createEavropCompensationApproval());

		assertEquals(EavropStateType.CLOSED, eavrop.getEavropState().getEavropStateType());
	}

	
	@Override
	Eavrop getEavrop() {
		return createApprovedEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		return EavropStateType.APPROVED;
	}
}
