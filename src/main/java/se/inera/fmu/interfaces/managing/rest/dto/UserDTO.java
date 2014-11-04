package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.ArrayList;
import java.util.List;

import se.inera.fmu.domain.model.authentication.Role;

public class UserDTO {
	private String hsaId;
	private String vardenhetHsaId;
	private String firstName;
	private String middleAndLastName;
	private List<Role> roles = new ArrayList<>();
	private Role activeRole;
	
	public String getHsaId() {
		return hsaId;
	}
	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}
	public String getVardenhetHsaId() {
		return vardenhetHsaId;
	}
	public void setVardenhetHsaId(String vardenhetHsaId) {
		this.vardenhetHsaId = vardenhetHsaId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleAndLastName() {
		return middleAndLastName;
	}
	public void setMiddleAndLastName(String middleAndLastName) {
		this.middleAndLastName = middleAndLastName;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Role getActiveRole() {
		return activeRole;
	}
	public void setActiveRole(Role activeRole) {
		this.activeRole = activeRole;
	}
}
