package se.inera.fmu.interfaces.managing.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.interfaces.managing.dtomapper.EavropDTOMapper;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.validation.ValidateLandstingCode;

import com.codahale.metrics.annotation.Timed;

/**
 * Created by Rasheed on 8/25/14.
 *
 * REST controller for tracking eavrops
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/app")
@Validated
public class EavropResource {

	@Inject
	private FmuOrderingService fmuOrderingService;
	
	private EavropDTOMapper eavropMapper = new EavropDTOMapper();
	
    /**
     *
     * TODO#1: Add parameter date range.
     * TODO#2: Add parameter status of utredning (new, in-process or finished)
     *
     * @return
     */
	@RequestMapping(
			value = "/rest/eavrop/landstingcode/{landstingCode}/fromdate/{startDate}/todate/{endDate}/status/{status}"
					+ "/page/{currentPage}/pagesize/{pageSize}/sortkey/{sortKey}/sortorder/{sortOrder}"
			, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public Integer getEavrops(
			@ValidateLandstingCode @PathVariable Integer landstingCode,
			@PathVariable Integer startDate, @PathVariable Integer endDate, 
			@PathVariable EavropStateType status, @PathVariable int currentPage, @PathVariable int pageSize,
			@PathVariable String sortKey, @PathVariable Direction sortOrder) {
		
		ResponseEntity<List<EavropDTO>> response = new ResponseEntity<List<EavropDTO>>(HttpStatus.OK);
		return landstingCode;
	}

}
