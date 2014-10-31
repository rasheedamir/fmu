package se.inera.fmu.domain.model.authentication;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void setActiveRole() {
		User u = new User();
		u.getRoles().add(Role.LANDSTINGSSAMORDNARE);
		u.getRoles().add(Role.UTREDARE);
		
		u.setActiveRole(Role.LANDSTINGSSAMORDNARE);
		assertEquals(Role.LANDSTINGSSAMORDNARE, u.getActiveRole());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setActiveRoleNonExistingShouldThrowException() {
		User u = new User();
		u.getRoles().add(Role.UTREDARE);
		
		u.setActiveRole(Role.LANDSTINGSSAMORDNARE);
	}	

}
