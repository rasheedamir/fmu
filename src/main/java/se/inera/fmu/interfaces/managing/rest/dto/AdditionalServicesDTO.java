package se.inera.fmu.interfaces.managing.rest.dto;

import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalServicesDTO {
	private BookingType name;
	private Long antalTimmar;
	private Boolean tolkBooked;
}
