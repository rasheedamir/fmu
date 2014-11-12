package se.inera.fmu.application.impl.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Created by Rasheed on 11/4/14.
 *
 * Command to create a new eavrop.
 */
@Getter
@AllArgsConstructor
public class CreateEavropCommand {

    @NonNull private ArendeId arendeId;
    @NonNull private UtredningType utredningType;
    private String interpreterLanguages;
    @NonNull private PersonalNumber personalNumber;
    @NonNull private Name invanareName;
    @NonNull private Gender invanareGender;
    @NonNull private Address invanareHomeAddress;
    private String invanareEmail;
    private String invanareSpecialNeeds;
    @NonNull private Landsting landsting;
    @NonNull private String administratorName;
    @NonNull private String administratorBefattning;
    @NonNull private String administratorOrganisation;
    @NonNull private String administratorEnhet;
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