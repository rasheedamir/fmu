package se.inera.fmu.application.impl.command;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Created by Rasheed on 11/4/14.
 *
 * Command to create a new eavrop.
 */
@Getter
@Setter
@AllArgsConstructor
public class CreateEavropCommand {

    private ArendeId arendeId;
    private UtredningType utredningType;
    private String interpreterLanguages;
    private PersonalNumber personalNumber;
    private Name invanareName;
    private Gender invanareGender;
    private Address invanareHomeAddress;
    private String invanareEmail;
    private String invanareSpecialNeeds;
    private Landsting landsting;
    private String administratorName;
    private String administratorBefattning;
    private String administratorOrganisation;
    private String administratorEnhet;
    private String administratorPhone;
    private String administratorEmail;
    private String description;
    private String utredningFocus;
    private String additionalInformation;
    private String priorExaminedAt;
    private String priorMedicalLeaveIssuedAt;
    private String priorMedicalLeaveIssuedByName;
    private String priorMedicalLeaveIssuedByBefattning;
    private String priorMedicalLeaveIssuedByOrganisation;
    private String priorMedicalLeaveIssuedByEnhet;
    private String priorMedicalLeaveIssuedByPhone;
    private String priorMedicalLeaveIssuedByEmail;
}