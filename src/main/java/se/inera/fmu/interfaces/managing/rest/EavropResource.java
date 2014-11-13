package se.inera.fmu.interfaces.managing.rest;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;
import se.inera.fmu.interfaces.managing.rest.validation.ValidPageSize;
import se.inera.fmu.interfaces.managing.rest.validation.ValidateDate;
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
	
	public static enum OverviewEavropStates {
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
			@PathVariable OverviewEavropStates status, 
			@ValidatePageNumber @PathVariable int currentPage, 
			@ValidPageSize @PathVariable int pageSize,
			@ValidateSortKey @PathVariable String sortKey, 
			@PathVariable Direction sortOrder) {
		
		Pageable pageSpecs = new PageRequest(currentPage, pageSize, new Sort(sortOrder, sortKey));
		EavropPageDTO pageEavrops = this.fmuOrderingService.getOverviewEavrops(startDate, endDate, status, pageSpecs);
		return new ResponseEntity<EavropPageDTO>(pageEavrops, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/rest/eavrop/{eavropId}/events"
			, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<EavropEventDTO>> getAllEavropEvents(@PathVariable String eavropId) {
		
		List<EavropEventDTO> retval = this.fmuOrderingService.getEavropEvents(eavropId);
		return new ResponseEntity<List<EavropEventDTO>>(retval, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/eavrop/{eavrop_id}/all-events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AllEventsDTO getAllEvents(@PathVariable("eavrop_id") String eavrop_id){
		return this.fmuOrderingService.getAllEvents(new EavropId(eavrop_id));
	}
	
	@RequestMapping(value="/rest/eavrop/{id}/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDTO getOrderInfo(@PathVariable("id") String id){
		return this.fmuOrderingService.getOrderInfo(new EavropId(id));
	}	
}
