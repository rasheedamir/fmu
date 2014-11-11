package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponse;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;

/*
 * Inteface of state controlled behaviour
 */
public interface EavropState{
	
	public static final EavropState[] NOT_ACCEPTED_STATES = {new  UnassignedEavropState(), new AssignedEavropState() };
	public static final EavropState[] ACCEPTED_STATES = {new  AcceptedEavropState(), new OnHoldEavropState() };
	public static final EavropState[] COMPLETED_STATES = {new  ApprovedEavropState(), new ClosedEavropState() };
	
	public EavropStateType getEavropStateType();
	
	//Assignment
	public void assignEavropToVardgivarenhet(Eavrop eavrop, Vardgivarenhet vardgivarenhet);
	
	public void acceptEavropAssignment(Eavrop eavrop);
	
	public void rejectEavropAssignment(Eavrop eavrop);
	
	//Documents
	public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop, DateTime documentsSentFromBestallareDateTime);
	
	public void addReceivedDocument(Eavrop eavrop, ReceivedDocument receivedDocument);
	
	public void addRequestedDocument(Eavrop eavrop, RequestedDocument requestedDocument);
	
	//Bookings
	public void addBooking(Eavrop eavrop, Booking booking);
	
	public void setBookingStatus(Eavrop eavrop, BookingId bookingId, BookingStatusType bookingStatus, Note cancellationNote);

	public void setInterpreterBookingStatus(Eavrop eavrop, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote);
	
	public void addBookingDeviationResponse(Eavrop eavrop, BookingId bookingId, BookingDeviationResponse bookingDeviationResponse);
	
	//Notes
	public void addNote(Eavrop eavrop, Note note);
	
	public void removeNote(Eavrop eavrop, Note note);
	
	//Intyg
	public void addIntygSignedInformation(Eavrop eavrop, IntygSignedInformation intygSignedInformation);

	public void addIntygComplementRequestInformation(Eavrop eavrop, IntygComplementRequestInformation intygComplementRequestInformation);
	
	public void addIntygApprovedInformation(Eavrop eavrop, IntygApprovedInformation intygApprovedInformation);
	
	//Approval
	public void approveEavrop(Eavrop eavrop, EavropApproval eavropApproval);

	public void approveEavropCompensation(Eavrop eavrop, EavropCompensationApproval eavropCompensationApproval);
	
}
