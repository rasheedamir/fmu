package se.inera.fmu.interfaces.managing.dtomapper;

import java.util.ArrayList;
import java.util.List;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.booking.Booking;
import se.inera.fmu.interfaces.managing.rest.dto.AdditionalServicesDTO;
import se.inera.fmu.interfaces.managing.rest.dto.CompensationDTO;

public class CompensationDTOMapper {
	public CompensationDTO map(Eavrop eavrop) {
		CompensationDTO dto = new CompensationDTO();
		dto.setArendeId(eavrop.getArendeId().toString());
		dto.setUtredningType(eavrop.getUtredningType() != null ? eavrop.getUtredningType().name()
				: null);
		dto.setUtforareOrganisation(eavrop.getCurrentAssignedVardgivarenhet() != null ? eavrop
				.getCurrentAssignedVardgivarenhet().getUnitName() : null);
		dto.setUtforareNamn(eavrop.getIntygSigningPerson() != null ? eavrop.getIntygSigningPerson()
				.getName() : null);
		dto.setTolkBooked(eavrop.hasInterpreterBooking());
		dto.setUtredningDuration(eavrop.getNoOfAssesmentDays());
		dto.setNrDaysAfterCompletetion(eavrop.getNoOfDaysUsedForLastComplementRequest());
		dto.setNrAvikelser(eavrop.getNumberOfDeviationsOnEavrop());
		dto.setNrUtredningstarts(eavrop.getNoOfEavropAssessmentsStarts());
		// 10. Utredning är komplett och godkänd?
		dto.setIsCompletedAndApproved(eavrop.getEavropCompensationApproval() != null
				&& eavrop.getEavropCompensationApproval().isApproved());
		// Specifikation avvikelser
		dto.setAvikelser(eavrop.getEavropDeviationEventDTOs());

		// Tilläggstjänster
		List<Booking> tillaggTjanster = eavrop.getAdditionalServiceBookings();
		if (tillaggTjanster == null) {
			return dto;
		}

		ArrayList<AdditionalServicesDTO> tillaggs = new ArrayList<AdditionalServicesDTO>();
		for (Booking booking : tillaggTjanster) {
			AdditionalServicesDTO data = new AdditionalServicesDTO();
			data.setName(booking.getBookingResource() != null ? booking.getBookingResource().getRole() : null);
			data.setAntalTimmar(booking.getBookingDuration());
			data.setTolkBooked(booking.hasInterpreterBooking());
			tillaggs.add(data);
		};

		dto.setTillaggTjanster(tillaggs);

		return dto;
	}
}
