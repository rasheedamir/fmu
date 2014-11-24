package se.inera.fmu.domain.model.authentication;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void setActiveRole() {
		User u = new User();
		u.getRoles().add(Role.ROLE_SAMORDNARE);
		u.getRoles().add(Role.ROLE_UTREDARE);
		
		u.setActiveRole(Role.ROLE_SAMORDNARE);
		assertEquals(Role.ROLE_SAMORDNARE, u.getActiveRole());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setActiveRoleNonExistingShouldThrowException() {
		User u = new User();
		u.getRoles().add(Role.ROLE_UTREDARE);
		
		u.setActiveRole(Role.ROLE_SAMORDNARE);
	}	

}
