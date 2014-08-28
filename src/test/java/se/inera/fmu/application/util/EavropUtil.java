package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;

/**
 * Created by Rasheed on 7/8/14.
 */
public final class EavropUtil {

    public static final ArendeId ARENDE_ID = new ArendeId("112233");
    public static final UtredningType UTREDNING_TYPE = UtredningType.AFU;
    public static final String TOLK = "ENGLISH";

    public static Eavrop createEavrop() {
        return new Eavrop(ARENDE_ID, UTREDNING_TYPE, TOLK, PatientUtil.createPatient());
    }
}
