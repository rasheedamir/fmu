package se.inera.fmu.interfaces.managing.rest;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.interfaces.managing.rest.dto.UserDTO;

import com.codahale.metrics.annotation.Timed;

/**
 * Created by Rasheed on 8/25/14.
 *
 * REST controller for tracking eavrops
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/app")
public class UserResource {

	@Inject
	private CurrentUserService userService;

    /**
     *
     * TODO#1: Add parameter date range.
     * TODO#2: Add parameter status of utredning (new, in-process or finished)
     *
     * @return
     */
	@RequestMapping(value = "/rest/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public UserDTO getCurrentUser() {
		User currentUser = userService.getCurrentUser();
		UserDTO dto = new UserDTO();
		
		BeanUtils.copyProperties(currentUser, dto);
		return dto;
	}
	
	@RequestMapping(value = "/rest/user/changerole", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void changeRole(@RequestParam(required=true) Role role) {
		userService.changeRole(role);
	}	
}
