package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BestallningEavropDTO extends EavropDTO{
	private String patientCity;
	private Integer antalDagarEfterForfragan;
	private String color;
}
