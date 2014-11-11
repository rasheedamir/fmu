package se.inera.fmu.application.impl;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproveEavropCompensationCommand {
	private ArendeId arendeId;
	private DateTime approveDateTime;
	private boolean approved;
	private String comment;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone;
    private String personEmail;
}
