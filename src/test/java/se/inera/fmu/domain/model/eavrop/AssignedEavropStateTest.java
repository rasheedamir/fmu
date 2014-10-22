package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;


public class AssignedEavropStateTest extends AbstractEavropStateTest{

	@Test
	@Override
	public void testAcceptEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.acceptEavropAssignment();
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testRejectEavropAssignment() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.rejectEavropAssignment();
		assertEquals(EavropStateType.UNASSIGNED, eavrop.getEavropState().getEavropStateType());
	}
	
	@Override
	Eavrop getEavrop() {
		return createAssignedEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		// TODO Auto-generated method stub
		return EavropStateType.ASSIGNED;
	}
}
