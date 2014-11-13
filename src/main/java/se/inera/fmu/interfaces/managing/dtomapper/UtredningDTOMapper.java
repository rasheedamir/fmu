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
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
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
		retval.addAll(mapCompensationApproval(eavrop.getEavropCompensationApproval()));
		return retval;
	}

	private List<HandelseDTO> mapCompensationApproval(
			EavropCompensationApproval eavropCompensationApproval) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();

		if (eavropCompensationApproval == null)
			return retval;

		HandelseDTO dto = new HandelseDTO();
		dto.setHandelse(NoneBookingEvents.EAVROP_COMPENSATION_APPROVED)
				.setDateOfEvent(
						eavropCompensationApproval.getCompensationDateTime() != null ? eavropCompensationApproval
								.getCompensationDateTime().getMillis() : null)
				.setUtredaPerson(
						eavropCompensationApproval.getPerson() != null ? eavropCompensationApproval
								.getPerson().getName() : null)
				.setRole(eavropCompensationApproval.getPerson() != null ? eavropCompensationApproval
								.getPerson().getRole() : null);

		retval.add(dto);
		return retval;
	}

	private List<HandelseDTO> mapEavropApproval(EavropApproval eavropApproval) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();
		if (eavropApproval == null)
			return retval;

		HandelseDTO dto = new HandelseDTO();
		dto.setHandelse(NoneBookingEvents.EAVROP_APPROVED)
				.setDateOfEvent(
						eavropApproval.getApprovalTimestamp() != null ? eavropApproval
								.getApprovalTimestamp().getMillis() : null)
				.setUtredaPerson(
						eavropApproval.getPerson() != null ? eavropApproval
								.getPerson().getName() : null)
				.setRole(eavropApproval.getPerson() != null ? eavropApproval
								.getPerson().getRole() : null);

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
			else if (info instanceof IntygSignedInformation)
				dto.setHandelse(NoneBookingEvents.INTYG_SIGNED);
			else
				dto.setHandelse(NoneBookingEvents.UNKNOWN);

			dto.setDateOfEvent(
					info.getInformationTimestamp() != null ? info
							.getInformationTimestamp().getMillis() : null)
					.setUtredaPerson(
							info.getPerson() != null ? info.getPerson()
									.getName() : null)
					.setRole(info.getPerson() != null ? info.getPerson()
									.getRole() : null);
			retval.add(dto);

		}

		return retval;
	}

	private List<HandelseDTO> mapBookings(Set<Booking> bookings, Eavrop eavrop) {
		List<HandelseDTO> retval = new ArrayList<HandelseDTO>();
		for (Booking booking : bookings) {
			HandelseDTO dto = new HandelseDTO();
			dto.setHandelse(booking.getBookingType());

			StatusDTO tolkstatus = new StatusDTO();
			StatusDTO handelseStatus = new StatusDTO();

			InterpreterBooking tolk = booking.getInterpreterBooking();
			tolkstatus
					.setCurrentStatus(tolk.getInterpreterBookingStatus())
					.setComment(
							tolk.getDeviationNote() != null ? tolk
									.getDeviationNote().getText() : null)
					.setStatuses(
							BookingStatusType.getValidBookingStatuses(
									eavrop.getUtredningType(),
									booking.getBookingType()).toArray());

			handelseStatus
					.setCurrentStatus(booking.getBookingStatus())
					.setComment(
							booking.getDeviationNote() != null ? booking
									.getDeviationNote().getText() : null)
					.setStatuses(
							BookingStatusType.getValidBookingStatuses(
									eavrop.getUtredningType(),
									booking.getBookingType()).toArray());

			DateTime datetime = booking.getStartDateTime();
			dto.setTolkStatus(tolkstatus)
					.setHandelseStatus(handelseStatus)
					.setDateOfEvent(
							datetime != null ? datetime.getMillis() : null)
					.setTimeOfEvent(
							datetime != null ? datetime.toLocalTime() : null)
					.setUtredaPerson(
							booking.getPerson() != null ? booking.getPerson()
									.getName() : null)
					.setRole(booking.getPerson() != null ? booking.getPerson()
							.getRole() : null);

			retval.add(dto);
		}

		return retval;
	}
}
