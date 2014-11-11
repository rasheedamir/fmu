package se.inera.fmu.application.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.ArendeId;

@Getter
@Setter
@AllArgsConstructor
public class ApproveEavropCommand {
	private ArendeId arendeId;
	private DateTime approveDateTime;
	private String comment;
	private String personName;
    private String personRole;
    private String personOrganisation;
    private String personUnit;
    private String personPhone;
    private String personEmail;

}
