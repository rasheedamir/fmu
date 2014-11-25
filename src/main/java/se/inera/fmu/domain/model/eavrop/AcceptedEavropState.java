package se.inera.fmu.domain.model.eavrop;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationTypeUtil;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
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
	
	@Override
	public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop, DateTime documentsSentFromBestallareDateTime){
		eavrop.setDocumentsSentFromBestallare(documentsSentFromBestallareDateTime);
		eavrop.doDateTimeDocumentsSentFromBestallare(documentsSentFromBestallareDateTime);
		//No state transition
	}
			
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
			throw new IllegalArgumentException(String.format("Booking %s on eavrop %s has no interpreter booked", bookingId.toString(), eavrop.getEavropId().toString()));
		}
		
		InterpreterBooking interpreter = booking.getInterpreterBooking();
		interpreter.setInterpreterBookingStatus(interpreterStatus);
		interpreter.setDeviationNote(cancelNote);
	}
	
	@Override
	public void addIntygSentInformation(Eavrop eavrop, IntygSentInformation intygSentInformation){
		eavrop.addToIntygSentInformation(intygSentInformation);
		
		//State transition ACCEPTED -> SENT
		eavrop.setEavropState(new SentEavropState());
	}
	
	@Override
	public void addReceivedDocument(Eavrop eavrop, ReceivedDocument receivedDocument) {
		eavrop.addToReceivedDocuments(receivedDocument);
		//No state transition
	}
	
	@Override
	public void addRequestedDocument(Eavrop eavrop, RequestedDocument requestedDocument) {
		eavrop.addToRequestedDocuments(requestedDocument);
		//No state transition
	}
}
