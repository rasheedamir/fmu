package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;


public class UnassignedEavropStateTest extends AbstractEavropStateTest{
	
	@Test
	@Override
	public void testAssignEavropToVardgivarenhet() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.assignEavropToVardgivarenhet(createVardgivarenhet());
		assertEquals(EavropStateType.ASSIGNED, eavrop.getEavropState().getEavropStateType());
	}

	@Override
	Eavrop getEavrop() {
		return createUnassignedEavrop();
	}

	@Override
	EavropStateType getEavropStateType() {
		// TODO Auto-generated method stub
		return EavropStateType.UNASSIGNED;
	}

	
}
