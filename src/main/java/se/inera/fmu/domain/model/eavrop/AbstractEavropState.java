package se.inera.fmu.domain.model.eavrop;

import java.io.Serializable;

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
 * Base class implementing the EavropState interface, 
 * since none of the behaviours of the interface is allowed until explicitly stated, this abstract base class implements the interface an throws illegal state exception
 * If behaviour should be allowed the specified state needs to override this method  
 */
public abstract class AbstractEavropState implements EavropState, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void assignEavropToVardgivarenhet(Eavrop eavrop, Vardgivarenhet vardgivarenhet) {
		throw new IllegalStateException("Method assignEavropToVardgivarenhet is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void acceptEavropAssignment(Eavrop eavrop) {
		throw new IllegalStateException(
				"Method acceptEavropAssignment is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void rejectEavropAssignment(Eavrop eavrop) {
		throw new IllegalStateException(
				"Method rejectEavropAssignment is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop,
			DateTime documentsSentFromBestallareDateTime) {
		throw new IllegalStateException(
				"Method setDocumentsSentFromBestallareDateTime is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void addReceivedDocument(Eavrop eavrop, ReceivedDocument receivedDocument) {
		throw new IllegalStateException(
				"Method addReceivedDocument is not available in state "
						+ this.getEavropStateType().name());
	}

	@Override
	public void addRequestedDocument(Eavrop eavrop, RequestedDocument requestedDocument) {
		throw new IllegalStateException(
				"Method addRequestedDocument is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void addBooking(Eavrop eavrop, Booking booking) {
		throw new IllegalStateException(
				"Method addBooking is not available in state "
						+ getEavropStateType().name());
	}

//	@Override
//	//public void cancelBooking(Eavrop eavrop, BookingId bookingId, BookingDeviation deviation) {
//	public void cancelBooking(Eavrop eavrop, BookingId bookingId, BookingStatusType cancellationType, Note cancellationNote){
//		throw new IllegalStateException(
//				"Method cancelBooking is not available in state "
//						+ getEavropStateType().name());
//	}

	@Override
	//public void cancelBooking(Eavrop eavrop, BookingId bookingId, BookingDeviation deviation) {
	public void setBookingStatus(Eavrop eavrop, BookingId bookingId, BookingStatusType bookingStatusType, Note cancellationNote){
		throw new IllegalStateException(
				"Method setBookingStatus is not available in state "
						+ getEavropStateType().name());
	}

	
	@Override
	public void setInterpreterBookingStatus(Eavrop eavrop, BookingId bookingId, InterpreterBookingStatusType interpreterStatus, Note cancellationNote){
		throw new IllegalStateException(
				"Method setBookingStatus is not available in state "
						+ getEavropStateType().name());
	}

	
	@Override
	public void addBookingDeviationResponse(Eavrop eavrop, BookingId bookingId,
			BookingDeviationResponse bookingDeviationResponse) {
		throw new IllegalStateException(
				"Method addBookingDeviationResponse is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void addNote(Eavrop eavrop, Note note) {
		throw new IllegalStateException(
				"Method addNote is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void removeNote(Eavrop eavrop, Note note) {
		throw new IllegalStateException(
				"Method removeNote is not available in state "
						+ getEavropStateType().name());
	}
	
	@Override
	public void addIntygSignedInformation(Eavrop eavrop,
			IntygSignedInformation intygSignedInformation) {
		throw new IllegalStateException(
				"Method addIntygSignedInformation is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void addIntygComplementRequestInformation(Eavrop eavrop,
			IntygComplementRequestInformation intygComplementRequestInformation) {
		throw new IllegalStateException(
				"Method addIntygComplementRequestInformation is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void addIntygApprovedInformation(Eavrop eavrop,
			IntygApprovedInformation intygApprovedInformation) {
		throw new IllegalStateException(
				"Method addIntygApprovedInformation is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void approveEavrop(Eavrop eavrop, EavropApproval eavropApproval) {
		throw new IllegalStateException(
				"Method approveEavrop is not available in state "
						+ getEavropStateType().name());
	}

	@Override
	public void approveEavropCompensation(Eavrop eavrop,
			EavropCompensationApproval eavropCompensationApproval) {
		throw new IllegalStateException(
				"Method approveEavropCompensation is not available in state "
						+ getEavropStateType().name());
	}
}
