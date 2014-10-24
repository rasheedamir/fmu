package se.inera.fmu.domain.model.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import se.inera.fmu.domain.shared.AbstractAuditingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A user.
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7285220478544998948L;
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

	@Override
    public String toString() {
        return hsaId;
    }
}
