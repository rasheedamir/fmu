package se.inera.fmu.interfaces.managing.dtomapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropApproval;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApproval;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.domain.model.eavrop.booking.BookingStatusType;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBooking;
import se.inera.fmu.domain.model.eavrop.booking.interpreter.InterpreterBookingStatusType;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.interfaces.managing.rest.dto.HandelseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.StatusDTO;

public class UtredningDTOMapper {
	public static enum NoneBookingEvents {
		INTYG_APPROVED, INTYG_COMPLEMENT_REQUEST, INTYG_SIGNED, EAVROP_APPROVED, EAVROP_COMPENSATION_APPROVED, UNKNOWN
	};

	public List<HandelseDTO> map(Eavrop eavrop) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();
		retval.addAll(mapBookings(eavrop.getBookings(), eavrop));
		retval.addAll(mapIntygInformations(eavrop.getIntygInformations()));
		retval.addAll(mapEavropApproval(eavrop.getEavropApproval()));
		retval.addAll(mapCompensationApproval(eavrop
				.getEavropCompensationApproval()));
		return retval;
	}

	private List<HandelseDTO> mapCompensationApproval(
			EavropCompensationApproval eavropCompensationApproval) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();

		if (eavropCompensationApproval == null)
			return retval;

		HandelseDTO dto = new HandelseDTO();
		DateTime timeStamp = eavropCompensationApproval
				.getCompensationDateTime();
		Person person = eavropCompensationApproval.getPerson();

		dto.setHandelse(NoneBookingEvents.EAVROP_COMPENSATION_APPROVED)
				.setDateOfEvent(
						timeStamp != null ? timeStamp.getMillis() : null)
				.setTimeOfEvent(timeStamp)
				.setUtredaPerson(person != null ? person.getName() : null)
				.setRole(person != null ? person.getRole() : null);

		retval.add(dto);
		return retval;
	}

	private List<HandelseDTO> mapEavropApproval(EavropApproval eavropApproval) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();
		if (eavropApproval == null)
			return retval;

		HandelseDTO dto = new HandelseDTO();
		DateTime timeStamp = eavropApproval.getApprovalTimestamp();
		Person person = eavropApproval.getPerson();

		dto.setHandelse(NoneBookingEvents.EAVROP_APPROVED)
				.setDateOfEvent(
						timeStamp != null ? timeStamp.getMillis() : null)
				.setTimeOfEvent(timeStamp)
				.setUtredaPerson(person != null ? person.getName() : null)
				.setRole(person != null ? person.getRole() : null);

		retval.add(dto);
		return retval;
	}

	private List<HandelseDTO> mapIntygInformations(
			Set<IntygInformation> intygInformations) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();

		if (intygInformations == null || intygInformations.isEmpty())
			return retval;

		for (IntygInformation info : intygInformations) {
			HandelseDTO dto = new HandelseDTO();

			if (info instanceof IntygApprovedInformation)
				dto.setHandelse(NoneBookingEvents.INTYG_APPROVED);
			else if (info instanceof IntygComplementRequestInformation)
				dto.setHandelse(NoneBookingEvents.INTYG_COMPLEMENT_REQUEST);
			else if (info instanceof IntygSentInformation)
				dto.setHandelse(NoneBookingEvents.INTYG_SIGNED);
			else
				dto.setHandelse(NoneBookingEvents.UNKNOWN);

			DateTime timeStamp = info.getInformationTimestamp();
			Person person = info.getPerson();
			dto.setDateOfEvent(timeStamp != null ? timeStamp.getMillis() : null)
					.setTimeOfEvent(timeStamp)
					.setUtredaPerson(person != null ? person.getName() : null)
					.setRole(person != null ? person.getRole() : null);
			retval.add(dto);

		}

		return retval;
	}

	private List<HandelseDTO> mapBookings(Set<Booking> bookings, Eavrop eavrop) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();
		for (Booking booking : bookings) {
			HandelseDTO dto = new HandelseDTO();
			DateTime startDateTime = booking.getStartDateTime();
			DateTime endDateTime = booking.getEndDateTime();
			Person person = booking.getPerson();
			dto.setHandelse(booking.getBookingType())
					.setBookingId(booking.getBookingId().getId())
					.setDateOfEvent(
							startDateTime != null ? startDateTime.getMillis() : null)
					.setTimeOfEvent(startDateTime != null ? startDateTime : null)
					.setTimeOfEventEnd(endDateTime != null ? endDateTime: null)
					.setUtredaPerson(person != null ? person.getName() : null)
					.setRole(person != null ? person.getRole() : null);
			;

			StatusDTO tolkstatus = new StatusDTO();
			StatusDTO handelseStatus = new StatusDTO();

			InterpreterBooking tolk = booking.getInterpreterBooking();
			tolkstatus
					.setCurrentStatus(
							tolk != null ? tolk.getInterpreterBookingStatus()
									: null)
					.setComment(
							tolk != null && tolk.getDeviationNote() != null ? tolk
									.getDeviationNote().getText() : null)
					.setStatuses(InterpreterBookingStatusType.values());

			handelseStatus
					.setCurrentStatus(booking.getBookingStatus())
					.setComment(
							booking.getDeviationNote() != null ? booking
									.getDeviationNote().getText() : null)
					.setStatuses(
							BookingStatusType.getValidBookingStatuses(
									eavrop.getUtredningType(),
									booking.getBookingType()).toArray());

			dto.setTolkStatus(tolkstatus).setHandelseStatus(handelseStatus);

			retval.add(dto);
		}

		return retval;
	}
}
