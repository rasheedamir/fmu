package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;

/**
 * Created by Rasheed on 7/8/14.
 */
public final class EavropUtil {

    public static final ArendeId ARENDE_ID = new ArendeId("112233");
    public static final UtredningType UTREDNING_TYPE = UtredningType.AFU;
    public static final String TOLK = "ENGLISH";
    public static final Landsting LANDSTING = new Landsting(new LandstingCode(1), "Stockholms läns landsting"); 
    public static final Bestallaradministrator HANDLAGGARE = new Bestallaradministrator("Per Handläggarson","Handläggare","Nordväst, Sundbyberg", "08-123456", "per.hanlaggarsson@fk.se"); 
    
    
    public static Eavrop createEavrop() {
    	
		return EavropBuilder.eavrop()
		.withArendeId(ARENDE_ID)
		.withUtredningType(UTREDNING_TYPE) 
		.withInvanare(InvanareUtil.createInvanare())
		.withLandsting(LANDSTING)
		.withBestallaradministrator(HANDLAGGARE)
		.build();
    }
}
