package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * The accepted state, means that the care giver has accepted and 
 * the case is ongoing, this is where all the action is. 
 * Available state transition is to On hold  or Approved state 
 */
public class AcceptedEavropState extends AbstractNoteableEavropState {

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.ACCEPTED;
	}
	
//	@Override
//	public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop, DateTime documentsSentFromBestallareDateTime){
//		
//		//TODO:Use adding of documents, if type is exteral, then check if start should be set
//		//TODO:Remove timestamp and amd only store documents? 
//		//TODO:Can this be sent multiple times and does it have any effect on start date
//		eavrop.setDocumentsSentFromBestallare(documentsSentFromBestallareDateTime);
//		
//		//TODO: SET new base date, use some kind of domain service
//		eavrop.setStartDate(documentsSentFromBestallareDateTime.plusDays(3).toLocalDate());
//		
//		eavrop.handleDocumentsSent();
//		
//		//No state transition
//		//TODO: what should happen if multiple calls to this method is made?
//		// Just set the latest date and let that be the base for the start date
//	}
			
	@Override
	public void addBooking(Eavrop eavrop, Booking booking){
		
		eavrop.addToBookings(booking);
		
		//No state transition
	}
	
	@Override
	public void setBookingStatus(Eavrop eavrop, BookingId bookingId, BookingStatusType bookingStatus, Note cancelNote) {
		Booking booking = eavrop.getBooking(bookingId);
		if(booking==null){
			throw new IllegalArgumentException(String.format("booking %s does not exist on eavrop: %s", bookingId.toString(), eavrop.getEavropId().toString()));
		}
		
		if(bookingStatus.isDeviant()){
			cancelBooking(eavrop, bookingId, bookingStatus, cancelNote);
		}else{
			booking.setBookingStatus(bookingStatus);
		}
	}

	
	
	private void cancelBooking(Eavrop eavrop, BookingId bookingId, BookingStatusType bookingStatus, Note cancelNote) {
		Booking booking = eavrop.getBooking(bookingId);
		
		if(booking.getBookingStatus().isDeviant()){
			//TODO: create separate state machine for bookings
			throw new IllegalArgumentException(String.format("Booking %s on eavrop %s is already cancelled", bookingId.toString(), eavrop.getEavropId().toString()));
		}
		
		booking.setBookingStatus(bookingStatus);
		booking.setDeviationNote(cancelNote);
		
		//TODO: BookingDeviationTypeUtil functionality should be moved to domain object
		if(BookingDeviationTypeUtil.isBookingStatusReasonForOnHold(booking.getBookingStatus(), eavrop.getUtredningType())){
			//State transition ACCEPTED -> ON_HOLD
			eavrop.setEavropState(new OnHoldEavropState());
		}
	}
	
	@Override
	public void setInterpreterBookingStatus(Eavrop eavrop, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancelNote) {
		Booking booking = eavrop.getBooking(bookingId);
		if(booking.getInterpreterBooking() == null){
			//TODO: create separate state machine for bookings
			throw new IllegalArgumentException(String.format("Booking %s on eavrop %s has no interpreter booked", bookingId.toString(), eavrop.getEavropId().toString()));
		}
		
		InterpreterBooking interpreter = booking.getInterpreterBooking();
		interpreter.setInterpreterBookingStatus(interpreterStatus);
		interpreter.setDeviationNote(cancelNote);
	}
	
//	@Override
//	public void approveEavrop(Eavrop eavrop, EavropApproval eavropApproval){
//		eavrop.setEavropApproval(eavropApproval);
//		
//		//State transition ACCEPTED -> APPROVED
//		eavrop.setEavropState(new ApprovedEavropState());
//	}
	
	@Override
	public void addIntygSignedInformation(Eavrop eavrop, IntygSignedInformation intygSignedInformation){
		eavrop.addToIntygSignedInformation(intygSignedInformation);
		
		//State transition ACCEPTED -> SENT
		eavrop.setEavropState(new SentEavropState());
	}
	
//	@Override
//	public void addIntygComplementRequestInformation(Eavrop eavrop, IntygComplementRequestInformation intygComplementRequestInformation){
//		eavrop.addToIntygComplementRequestInformation(intygComplementRequestInformation);
//		//No state transition
//	}
//	
//	@Override
//	public void addIntygApprovedInformation(Eavrop eavrop, IntygApprovedInformation intygApprovedInformation){
//		eavrop.addToIntygApprovedInformation(intygApprovedInformation);
//		//No state transition
//	}
	
	@Override
	public void addReceivedDocument(Eavrop eavrop, ReceivedDocument receivedDocument) {
		eavrop.addToReceivedDocuments(receivedDocument);
	}
	
	@Override
	public void addRequestedDocument(Eavrop eavrop, RequestedDocument requestedDocument) {
		eavrop.addToRequestedDocuments(requestedDocument);
		//No state transition
	}
}
