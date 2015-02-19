package se.inera.fmu.application;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;

/**
 * Helper service for simulating customer interactions without having to send the actual webservice call. 
 * To be used as a testing utility and to be called via REST calls from the gui. 
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
