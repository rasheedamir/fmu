package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;


/**
 * Created by Rasheed on /9/14.
 */
public final class LandstingUtil {

    public static final LandstingCode LANDSTING_ID = new LandstingCode(1);
    public static final String NAME = "Stocholms Läns Landsting";

    public static Landsting createLandsting() {
        return new Landsting(LANDSTING_ID, NAME);
    }
}
