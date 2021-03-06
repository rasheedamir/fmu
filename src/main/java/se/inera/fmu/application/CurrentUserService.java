package se.inera.fmu.application;

import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;

public interface CurrentUserService {
	User getCurrentUser();
	void changeRole(Role role);
}
