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

public interface FmuFacade {
	
	public EavropPageDTO getOverviewEavrops(long fromDate, long toDate, OverviewEavropStates state,	Pageable paginationSpecs);
	
	public List<HandelseDTO> getEavropEvents(String eavropId);

	public AllEventsDTO getAllEvents(EavropId eavropId);

	public OrderDTO getOrderInfo(EavropId eavropId);

	public List<ReceivedDocumentDTO> getReceivedDocuments(EavropId eavropId);

	public List<RequestedDocumentDTO> getRequestedDocuments(EavropId eavropId);

	public List<NoteDTO> getNotes(EavropId eavropId);
	
	public Eavrop getEavropForUser(EavropId eavropId);

	public void addReceivedDocuments(EavropId eavropId, ReceivedDocumentDTO doc);

	public void addRequestedDocuments(EavropId eavropId, RequestedDocumentDTO doc);

	public void addBooking(BookingRequestDTO changeRequestDto);
	
	public void modifyBooking(BookingModificationRequestDTO changeRequestData);

	public void modifyTolkBooking(TolkBookingModificationRequestDTO changeRequestData);

	public void addNote(AddNoteRequestDTO addRequest);

	public void removeNote(String eavropId, String noteId);

	public PatientDTO getPatientInfo(EavropId eavropId);

	public EavropDTO getEavrop(EavropId eavropId);

	public List<VardgivarenhetDTO> getVardgivarenheter(EavropId eavropId);

	public void assignVardgivarenhet(EavropId eavropId, Long vardgivarenhetId);

	public void acceptEavropAssignment(EavropId eavropId);

	public void rejectEavropAssignment(EavropId eavropId);

	public CompensationDTO getCompensations(EavropId eavropId);

}
