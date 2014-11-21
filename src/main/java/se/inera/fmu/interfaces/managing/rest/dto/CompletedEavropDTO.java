package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedEavropDTO extends EavropBaseDTO{
	private Integer dagarFromStartToAccepted;
	private Integer totalCompletionDays;
	private Integer avikelser;
	private String ansvarigUtredare;
	private String utredareOrganisation;
	private Long dateIntygDelivered;
	private Boolean isCompleted;
	private String compensationApprovedStatusAndDate;
}
