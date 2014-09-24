package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.invanare.Invanare;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingId;
import se.inera.fmu.domain.party.Bestallaradministrator;

/**
 * Created by Rasheed on 7/8/14.
 */
public final class EavropUtil {

    public static final ArendeId ARENDE_ID = new ArendeId("112233");
    public static final UtredningType UTREDNING_TYPE = UtredningType.AFU;
    public static final String TOLK = "ENGLISH";
    public static final Landsting LANDSTING = new Landsting(new LandstingId(1), "Stockholms läns landsting"); 
    public static final Bestallaradministrator HANDLAGGARE = new Bestallaradministrator("Per Handläggarson","Handläggare","Nordväst, Sundbyberg", "08-123456", "per.hanlaggarsson@fk.se"); 
    
    
    public static Eavrop createEavrop() {
        return new Eavrop(ARENDE_ID, UTREDNING_TYPE, InvanareUtil.createInvanare(), LANDSTING, HANDLAGGARE);

    }
}
