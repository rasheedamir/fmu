package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ÄrendeId;

/**
 * Created by Rasheed on 7/8/14.
 */
public final class EavropUtil {

    public static final ÄrendeId ÄRENDE_ID = new ÄrendeId("112233");
    public static final UtredningType UTREDNING_TYPE = UtredningType.AFU;
    public static final String TOLK = "ENGLISH";

    public static Eavrop createEavrop() {
        return new Eavrop(ÄRENDE_ID, UTREDNING_TYPE, TOLK, PatientUtil.createPatient());
    }
}
