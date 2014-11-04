package se.inera.fmu.application;

import java.util.List;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface FmuOrderingService {

    /**
     * Registers a new Eavrop in the tracking system.
     *
     * @param arendeId
     * @param utredningType
     * @param tolk
     * @param personalNumber
     * @param invanareName
     * @param invanareGender
     * @param invanareHomeAddress
     * @param invanareEmail
     * @param invanareSpecialNeeds
     * @param landsting
     * @param administratorName
     * @param administratorBefattning
     * @param administratorOrganisation
     * @param administratorPhone
     * @param administratorEmail
     * @return
     */
    ArendeId createNewEavrop(ArendeId arendeId,  UtredningType utredningType, String tolk, PersonalNumber personalNumber,
            Name invanareName, Gender invanareGender, Address invanareHomeAddress,
            String invanareEmail, String invanareSpecialNeeds, Landsting landsting, String administratorName, 
            String administratorBefattning, String administratorOrganisation, String administratorEnhet, String administratorPhone, 
            String administratorEmail);

}
