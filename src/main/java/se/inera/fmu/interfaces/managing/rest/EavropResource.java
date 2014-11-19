package se.inera.fmu.interfaces.managing.rest;

import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.interfaces.managing.rest.dto.AddNoteRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.HandelseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.NoteDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PatientDTO;
import se.inera.fmu.interfaces.managing.rest.dto.ReceivedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.RequestedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TimeDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TolkBookingModificationRequestDTO;
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

	@Inject
	private CurrentUserService currentUserService;

	public static enum OverviewEavropStates {
		NOT_ACCEPTED, ACCEPTED, COMPLETED
	}

	@RequestMapping(value = "/rest/eavrop/fromdate/{startDate}/todate/{endDate}/status/{status}"
			+ "/page/{currentPage}/pagesize/{pageSize}/sortkey/{sortKey}/sortorder/{sortOrder}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<EavropPageDTO> getEavrops(@ValidateDate @PathVariable Long startDate,
			@ValidateDate @PathVariable Long endDate, @PathVariable OverviewEavropStates status,
			@ValidatePageNumber @PathVariable int currentPage,
			@ValidPageSize @PathVariable int pageSize,
			@ValidateSortKey @PathVariable String sortKey, @PathVariable Direction sortOrder) {

		Pageable pageSpecs = new PageRequest(currentPage, pageSize, new Sort(sortOrder, sortKey));
		EavropPageDTO pageEavrops = this.fmuOrderingService.getOverviewEavrops(startDate, endDate,
				status, pageSpecs);
		return new ResponseEntity<EavropPageDTO>(pageEavrops, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/eavrop/{eavropId}/utredning", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<HandelseDTO>> getAllEavropEvents(@PathVariable String eavropId) {

		List<HandelseDTO> retval = this.fmuOrderingService.getEavropEvents(eavropId);
		return new ResponseEntity<List<HandelseDTO>>(retval, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/eavrop/{id}/all-events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AllEventsDTO getAllEvents(@PathVariable("id") String id) {
		return this.fmuOrderingService.getAllEvents(new EavropId(id));
	}

	@RequestMapping(value = "/rest/eavrop/{id}/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDTO getOrderInfo(@PathVariable("id") String id) {
		return this.fmuOrderingService.getOrderInfo(new EavropId(id));
	}
	
	@RequestMapping(value = "/rest/eavrop/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EavropDTO getEavrop(@PathVariable("id") String id) {
		return this.fmuOrderingService.getEavrop(new EavropId(id));
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/patient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PatientDTO getPatientInfo(@PathVariable("id") String id) {
		return this.fmuOrderingService.getPatientInfo(new EavropId(id));
	}	

	@RequestMapping(value = "/rest/eavrop/{id}/received-documents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ReceivedDocumentDTO> getReceivedDocuments(@PathVariable("id") String id) {
		return this.fmuOrderingService.getReceivedDocuments(new EavropId(id));
	}

	@RequestMapping(value="/rest/eavrop/{id}/received-documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addReceivedDocuments(@PathVariable("id") String id, @RequestBody ReceivedDocumentDTO doc){
		this.fmuOrderingService.addReceivedDocuments(new EavropId(id), doc);
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/requested-documents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RequestedDocumentDTO> getRequestedDocuments(@PathVariable("id") String id) {
		return this.fmuOrderingService.getRequestedDocuments(new EavropId(id));
	}
	
	@RequestMapping(value="/rest/eavrop/{id}/requested-documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addReceivedDocuments(@PathVariable("id") String id, @RequestBody RequestedDocumentDTO doc){
		this.fmuOrderingService.addRequestedDocuments(new EavropId(id), doc);
	}		

	@RequestMapping(value = "/rest/eavrop/{id}/notes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoteDTO> getNotes(@PathVariable("id") String id) {
		return this.fmuOrderingService.getNotes(new EavropId(id));
	}

	@RequestMapping(value = "/rest/eavrop/utredning/create/booking", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addBooking(@RequestBody final BookingRequestDTO booking) throws Exception {
		this.fmuOrderingService.addBooking(booking);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/rest/eavrop/utredning/modify/booking", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus modifyBooking(
			@RequestBody final BookingModificationRequestDTO changeRequestData) throws Exception {
		this.fmuOrderingService.modifyBooking(changeRequestData);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/rest/eavrop/utredning/modify/tolk", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus modifyBooking(
			@RequestBody final TolkBookingModificationRequestDTO changeRequestData)
			throws Exception {
		this.fmuOrderingService.modifyTolkBooking(changeRequestData);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/rest/eavrop/note/add", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addNote(
			@RequestBody final AddNoteRequestDTO addRequest)
			throws Exception {
		this.fmuOrderingService.addNote(addRequest);
		return HttpStatus.OK;
	}
}
