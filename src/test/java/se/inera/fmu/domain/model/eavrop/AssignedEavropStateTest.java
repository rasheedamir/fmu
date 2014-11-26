package se.inera.fmu.domain.model.eavrop;

import static org.junit.Assert.*;

import org.junit.Test;

import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.person.HoSPerson;


public class AssignedEavropStateTest extends AbstractEavropStateTest{

	@Test
	@Override
	public void testAcceptEavrop() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.acceptEavropAssignment(createHoSPerson());
		assertEquals(EavropStateType.ACCEPTED, eavrop.getEavropState().getEavropStateType());
	}

	@Test
	@Override
	public void testRejectEavropAssignment() {
		Eavrop eavrop = getEavrop();
		assertEquals(getEavropStateType(), eavrop.getEavropState().getEavropStateType());
		eavrop.rejectEavropAssignment(createHoSPerson(), "TEST");
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
