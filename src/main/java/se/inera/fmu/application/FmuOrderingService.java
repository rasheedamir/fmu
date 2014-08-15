package se.inera.fmu.application;

import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ÄrendeId;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Gender;
import se.inera.fmu.domain.model.patient.Name;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface FmuOrderingService {

    /**
     * Registers a new Eavrop in the tracking system.
     *
     * @param ärendeId
     * @param utredningType
     * @param tolk
     * @param personalNumber
     * @param patientName
     * @param patientGender
     * @param patientHomeAddress
     * @param patientEmail
     * @return
     */
    ÄrendeId createNewEavrop(ÄrendeId ärendeId, UtredningType utredningType, String tolk, String personalNumber,
                             Name patientName, Gender patientGender, Address patientHomeAddress, String patientEmail);

}
