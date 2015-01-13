package se.inera.fmu.facade;

import java.util.List;

import org.springframework.data.domain.Pageable;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
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

/**
 * Interface implemented by the facade.
 *
 * @author Rickard
 */
public interface FmuFacade {
	
	public EavropPageDTO getOverviewEavrops(long fromDate, long toDate, OverviewEavropStates state,	Pageable paginationSpecs);
	
	/****************************************************************
	 * Eavrop event/händelse methods.
	 ****************************************************************/
	
	/**
	 * Returns a list of helnall defined demand sources for the warehouse with the supplied id
	 *
	 * @param warehouseId warehouse to get demand sources for
	 * @return list of demand sources
	 */
	public List<HandelseDTO> getEavropEvents(String eavropId);

	public AllEventsDTO getAllEvents(EavropId eavropId);

	
	public Eavrop getEavropForUser(EavropId eavropId);


	public OrderDTO getOrderInfo(EavropId eavropId);

	
	
	public void addReceivedDocuments(EavropId eavropId, ReceivedDocumentDTO doc);

	public List<ReceivedDocumentDTO> getReceivedDocuments(EavropId eavropId);

	public void addRequestedDocuments(EavropId eavropId, RequestedDocumentDTO doc);

	public List<RequestedDocumentDTO> getRequestedDocuments(EavropId eavropId);


	public void addBooking(BookingRequestDTO changeRequestDto);
	
	public void modifyBooking(BookingModificationRequestDTO changeRequestData);

	public void modifyTolkBooking(TolkBookingModificationRequestDTO changeRequestData);

	
	public void addNote(AddNoteRequestDTO addRequest);

	public void removeNote(String eavropId, String noteId);
	
	public List<NoteDTO> getNotes(EavropId eavropId);


	public PatientDTO getPatientInfo(EavropId eavropId);

	public EavropDTO getEavrop(EavropId eavropId);

	public List<VardgivarenhetDTO> getVardgivarenheter(EavropId eavropId);
	
	/****************************************************************
	 * Assignment methods.
	 ****************************************************************/

	/**
	 * Assign Eavrop to a care giver. 
	 *
	 * @param eavropId The id of the eavrop about to be assigned 
	 * @param vardgivarenhetId The id of the care giver&vardgivare about to get assignment. 
	 */
	public void assignVardgivarenhetToEavrop(EavropId eavropId, Long vardgivarenhetId);

	/**
	 * Accept Eavrop assignment. 
	 *
	 * @param eavropId The id of the eavrop about to be accepted 
	 */
	public void acceptEavropAssignment(EavropId eavropId);

	/**
	 * Reject Eavrop assignment. 
	 *
	 * @param eavropId The id of the eavrop about to be rejected 
	 */
	public void rejectEavropAssignment(EavropId eavropId);

	
	
	public CompensationDTO getCompensations(EavropId eavropId);

}
