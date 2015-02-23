package se.inera.fmu.interfaces.managing.rest;

import java.util.List;

import javax.inject.Inject;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.facade.FmuFacade;
import se.inera.fmu.interfaces.managing.rest.dto.AddNoteRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.CompensationDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.HandelseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.NoteDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PatientDTO;
import se.inera.fmu.interfaces.managing.rest.dto.ReceivedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.RequestedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TolkBookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.VardgivarenhetDTO;
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
@RestController
@RequestMapping("/app")
@Validated
public class EavropResource {

	@Inject
	private FmuFacade fmuFacade;
	
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
		EavropPageDTO pageEavrops = this.fmuFacade.getOverviewEavrops(startDate, endDate,
				status, pageSpecs);
		return new ResponseEntity<EavropPageDTO>(pageEavrops, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/eavrop/{eavropId}/utredning", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<HandelseDTO>> getAllEavropEvents(@PathVariable String eavropId) {

		List<HandelseDTO> retval = this.fmuFacade.getEavropEvents(eavropId);
		return new ResponseEntity<List<HandelseDTO>>(retval, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/eavrop/{id}/all-events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public AllEventsDTO getAllEvents(@PathVariable("id") String id) {
		return this.fmuFacade.getAllEvents(new EavropId(id));
	}

	@RequestMapping(value = "/rest/eavrop/{id}/order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderDTO getOrderInfo(@PathVariable("id") String id) {
		return this.fmuFacade.getOrderInfo(new EavropId(id));
	}
	
	@RequestMapping(value = "/rest/eavrop/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EavropDTO getEavrop(@PathVariable("id") String id) {
		return this.fmuFacade.getEavrop(new EavropId(id));
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/patient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PatientDTO getPatientInfo(@PathVariable("id") String id) {
		return this.fmuFacade.getPatientInfo(new EavropId(id));
	}	

	@RequestMapping(value = "/rest/eavrop/{id}/received-documents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ReceivedDocumentDTO> getReceivedDocuments(@PathVariable("id") String id) {
		return this.fmuFacade.getReceivedDocuments(new EavropId(id));
	}

	@RequestMapping(value="/rest/eavrop/{id}/received-documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addReceivedDocuments(@PathVariable("id") String id, @RequestBody ReceivedDocumentDTO doc){
		this.fmuFacade.addReceivedDocuments(new EavropId(id), doc);
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/requested-documents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RequestedDocumentDTO> getRequestedDocuments(@PathVariable("id") String id) {
		return this.fmuFacade.getRequestedDocuments(new EavropId(id));
	}
	
	@RequestMapping(value = "/rest/eavrop/{id}/vardgivarenheter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VardgivarenhetDTO> getVardgivarenheter(@PathVariable("id") String id) {
		return this.fmuFacade.getVardgivarenheter(new EavropId(id));
	}	
	
	@RequestMapping(value="/rest/eavrop/{id}/requested-documents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addReceivedDocuments(@PathVariable("id") String id, @RequestBody RequestedDocumentDTO doc){
		this.fmuFacade.addRequestedDocuments(new EavropId(id), doc);
	}		

	@RequestMapping(value = "/rest/eavrop/{id}/notes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoteDTO> getNotes(@PathVariable("id") String id) {
		return this.fmuFacade.getNotes(new EavropId(id));
	}
	
	@RequestMapping(value = "/rest/eavrop/{id}/assign", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void assignVardgivarenhet(@PathVariable("id") String id, @RequestParam Long veId) {
		this.fmuFacade.assignVardgivarenhetToEavrop(new EavropId(id), veId);
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/accept", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void acceptRequest(@PathVariable("id") String id) {
		this.fmuFacade.acceptEavropAssignment(new EavropId(id));
	}	
	
	@RequestMapping(value = "/rest/eavrop/{id}/reject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void rejectRequest(@PathVariable("id") String id) {
		this.fmuFacade.rejectEavropAssignment(new EavropId(id));
	}		

	@RequestMapping(value = "/rest/eavrop/utredning/create/booking", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addBooking(@RequestBody final BookingRequestDTO booking) throws Exception {
		this.fmuFacade.addBooking(booking);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/rest/eavrop/utredning/modify/booking", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus modifyBooking(
			@RequestBody final BookingModificationRequestDTO changeRequestData) throws Exception {
		this.fmuFacade.modifyBooking(changeRequestData);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/rest/eavrop/utredning/modify/tolk", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus modifyBooking(
			@RequestBody final TolkBookingModificationRequestDTO changeRequestData)
			throws Exception {
		this.fmuFacade.modifyTolkBooking(changeRequestData);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/rest/eavrop/note/add", method = RequestMethod.POST)
	@ResponseBody
	public HttpStatus addNote(
			@RequestBody final AddNoteRequestDTO addRequest)
			throws Exception {
		this.fmuFacade.addNote(addRequest);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/rest/eavrop/{eavropId}/note/{noteId}/remove", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus removeNote(@PathVariable String eavropId, @PathVariable String noteId) throws Exception {
		this.fmuFacade.removeNote(eavropId, noteId);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/rest/eavrop/{id}/compensations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CompensationDTO getCompensations(@PathVariable("id") String id) {
		return this.fmuFacade.getCompensations(new EavropId(id));
	}
	
}
