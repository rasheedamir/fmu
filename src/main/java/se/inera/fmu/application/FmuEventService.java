package se.inera.fmu.application;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRestartedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAcceptedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.assignment.EavropRejectedByVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingCreatedEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationType;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingDeviationEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentRequestedEvent;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplemetsRequestedFromBestallareEvent;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentEvent;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;

/**
 * Service for reading entities
 * 
 * @author Rickard on 16/01/15.
 *
 */
public interface FmuEventService {

    public void publishEavropCreatedEvent(ArendeId arendeId);
    
    public void publishEavropAssignedToVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId);
    
    public void publishEavropAcceptedByVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId);
    
    public void publishEavropRejectedByVardgivarenhetEvent(EavropId eavropId, HsaId vardgivarenhetId);
    
    public void publishDocumentsSentByBestallareEvent(ArendeId arendeId, DateTime dateTimeDocumentsSent);
    
    public void publishEavropStartEvent(ArendeId arendeId);
    
	public void publishEavropRestartedByBestallareEvent(EavropId eavropId);

	public void publishEavropClosedByBestallareEvent(EavropId eavropId);
	
	public void publishDocumentRequestedEvent(EavropId eavropId, RequestedDocument requestedDocument);

	public void publishBookingCreatedEvent(EavropId eavropId, BookingId bookingId);
	
	public void publishBookingDeviationEvent(EavropId eavropId, BookingId bookingId);
	
	public void publishInterpreterBookingDeviationEvent(EavropId eavropId, BookingId bookingId);
	
	public void publishIntygSentEvent(EavropId eavropId, ArendeId arendeId, DateTime intygSentDateTime);

	public void publishIntygComplemetsRequestedFromBestallareEvent(EavropId eavropId, DateTime intygComplementRequestDateTime);

	public void publishIntygApprovedByBestallareEvent(EavropId eavropId, DateTime intygApprovedDateTime);
	
	public void publishEavropApprovedByBestallareEvent(EavropId eavropId, DateTime approvedDateTime);
	
	public void publishEavropCompensationApprovedByBestallareEvent(EavropId eavropId, DateTime compensationApprovalDateTime);
}
