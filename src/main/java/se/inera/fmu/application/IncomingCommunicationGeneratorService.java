package se.inera.fmu.application;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

/**
 * Interface implemented by the facade.
 *
 * @author Rickard
 */
public interface IncomingCommunicationGeneratorService {

	public ArendeId generateEavrop();
	
	public ArendeId generateSentDocuments(ArendeId arendeId);
	
	public ArendeId generateBookingDeviationResponse(ArendeId arendeId, BookingId bookingId, BookingDeviationResponseType bookingDeviationResponseType);
	
	public ArendeId generateIntygSent(ArendeId arendeId);

	public ArendeId generateIntygComplementRequestInformation(ArendeId arendeId);

	public ArendeId generateIntygApprovedInformation(ArendeId arendeId);

	public ArendeId generateEavropApproval(ArendeId arendeId);

	public ArendeId generateEavropCompensationApproval(ArendeId arendeId, boolean isApproved);

}
