package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagaendeEavropDTO extends EavropBaseDTO{
	private String color;
	private Long startDate;
	private Integer nrOfDaysSinceStart;
	private Integer avikelser;
}
