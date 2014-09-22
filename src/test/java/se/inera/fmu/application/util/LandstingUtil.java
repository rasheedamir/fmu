package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingId;


/**
 * Created by Rasheed on /9/14.
 */
public final class LandstingUtil {

    public static final LandstingId LANDSTING_ID = new LandstingId(1);
    public static final String NAME = "Stocholms Läns Landsting";

    public static Landsting createLandsting() {
        return new Landsting(LANDSTING_ID, NAME);
    }
}
