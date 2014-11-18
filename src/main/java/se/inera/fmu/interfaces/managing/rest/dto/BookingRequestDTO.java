package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;

@Getter
@Setter
public class BookingRequestDTO {
	private String eavropId;
    private BookingType bookingType;
    private Long bookingDate;
    private TimeDTO bookingStartTime;
    private TimeDTO bookingEndTime;
    private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private Boolean useInterpreter;
    private Boolean additionalService;
}
