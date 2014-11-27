package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalServicesDTO {
	private String name;
	private Long antalTimmar;
	private Boolean tolkBooked;
}
