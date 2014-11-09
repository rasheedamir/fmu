package se.inera.fmu.interfaces.managing.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.interfaces.managing.dtomapper.EavropDTOMapper;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.validation.ValidPageSize;
import se.inera.fmu.interfaces.managing.rest.validation.ValidateDate;
import se.inera.fmu.interfaces.managing.rest.validation.ValidateLandstingCode;
import se.inera.fmu.interfaces.managing.rest.validation.ValidatePageNumber;
import se.inera.fmu.interfaces.managing.rest.validation.ValidateSortKey;

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
@Slf4j
public class EavropResource {

	@Inject
	private FmuOrderingService fmuOrderingService;
	
	public static enum OVERVIEW_EAVROPS_STATES {
		NOT_ACCEPTED,
		ACCEPTED,
		COMPLETED
	}

	@RequestMapping(
			value = "/rest/eavrop/fromdate/{startDate}/todate/{endDate}/status/{status}"
					+ "/page/{currentPage}/pagesize/{pageSize}/sortkey/{sortKey}/sortorder/{sortOrder}"
			, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EavropPageDTO> getEavrops(
			@ValidateDate @PathVariable Long startDate, 
			@ValidateDate @PathVariable Long endDate, 
			@PathVariable OVERVIEW_EAVROPS_STATES status, 
			@ValidatePageNumber @PathVariable int currentPage, 
			@ValidPageSize @PathVariable int pageSize,
			@ValidateSortKey @PathVariable String sortKey, 
			@PathVariable Direction sortOrder) {
		Pageable pageSpecs = new PageRequest(currentPage, pageSize, new Sort(sortOrder, sortKey));
		EavropPageDTO pageEavrops = this.fmuOrderingService.getOverviewEavrops(startDate, endDate, status, pageSpecs);
		return new ResponseEntity<EavropPageDTO>(pageEavrops, HttpStatus.OK);
	}

}
