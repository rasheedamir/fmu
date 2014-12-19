package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.person.Bestallaradministrator;

/**
 * Created by Rasheed on 7/8/14.
 */
public final class EavropPropertiesUtil {

	private static final int PROP_START_DATE_OFFSET = 3;
	private static final int PROP_ACCEPTANCE_VALID_LENGTH = 5;
	private static final int PROP_ASSESSMENT_VALID_LENGTH = 25;
	private static final int PROP_COMPLETION_VALID_LENGTH = 10;
    
    public static EavropProperties createEavropProperties() {
    	
		return new EavropProperties(PROP_START_DATE_OFFSET, PROP_ACCEPTANCE_VALID_LENGTH, PROP_ASSESSMENT_VALID_LENGTH, PROP_COMPLETION_VALID_LENGTH);
    }
}
