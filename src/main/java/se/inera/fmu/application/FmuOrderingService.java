package se.inera.fmu.application;

import java.util.List;

import org.springframework.data.domain.Pageable;

import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.dto.AddNoteRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
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
 * Created by Rasheed on 7/7/14.
 */
public interface FmuOrderingService {

    /**
     * Registers a new Eavrop in the tracking system.
     * @param aCommand
     * @return
     */
    public ArendeId createEavrop(CreateEavropCommand aCommand);

	EavropPageDTO getOverviewEavrops(long fromDateMillis, long toDateMillis, OverviewEavropStates status, Pageable paginationSpecs);

	public List<HandelseDTO> getEavropEvents(String eavropId);

	public AllEventsDTO getAllEvents(EavropId eavropId);

	public OrderDTO getOrderInfo(EavropId eavropId);

	public List<ReceivedDocumentDTO> getReceivedDocuments(EavropId eavropId);

	public List<RequestedDocumentDTO> getRequestedDocuments(EavropId eavropId);

	public List<NoteDTO> getNotes(EavropId eavropId);
	public void addBooking(BookingRequestDTO booking);

	public void modifyBooking(BookingModificationRequestDTO changeRequestData);

	public void modifyTolkBooking(TolkBookingModificationRequestDTO changeRequestData);
	public void addReceivedDocuments(EavropId eavropId,
			ReceivedDocumentDTO doc);

	public void addRequestedDocuments(EavropId eavropId,
			RequestedDocumentDTO doc);

	public void addNote(AddNoteRequestDTO addRequest);
	
	public PatientDTO getPatientInfo(EavropId eavropId);

	public EavropDTO getEavrop(EavropId eavropId);

	public void removeNote(String eavropId, String noteId);

	public List<VardgivarenhetDTO> getVardgivarenheter(EavropId eavropId);

	public void assignVardgivarenhet(EavropId eavropId, Long veId);

	public void acceptRequest(EavropId eavropId);

	public void rejectRequest(EavropId eavropId);

}
