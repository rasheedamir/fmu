package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.person.Bestallaradministrator;


/**
 * Created by Rasheed on 8/7/14.
 */
public final class BestallaradministratorUtil {

    public static final String NAME = "Per Elofsson";
    public static final String BEFATTNING = "Handläggare";
    public static final String ORGANISATION = "Försäkringskassan";
    public static final String UNIT = "LFC Kalmar";
    public static final String PHONE = "555-132132";
    public static final String EMAIL = "per.elofsson@fk.se";

    public static Bestallaradministrator createBestallaradministrator() {
        return new Bestallaradministrator(NAME, BEFATTNING, ORGANISATION, UNIT, PHONE, EMAIL);
    }
}
