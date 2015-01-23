package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;


/**
 * Created by Rasheed on 8/7/14.
 */
public final class InvanareUtil {

    public static final String PERSONAL_NUMBER_STRING = "19900208-9009";
    public static final PersonalNumber PERSONAL_NUMBER = new PersonalNumber(PERSONAL_NUMBER_STRING);
    public static final Name NAME = new Name("FRODO", "", "BAGINS");
    public static final Gender GENDER = Gender.MALE;
    public static final Address HOME_ADDRESS = new Address("HOME # 1", "STREET # 2", "8899", "VEVEY", "SWITZERLAND");
    public static final String PHONE = "555-12345";
    public static final String EMAIL = "frodo@bagins.com";
    public static final String SPECIAL_NEED = null;

    public static Invanare createInvanare() {
        return new Invanare(PERSONAL_NUMBER, NAME, GENDER, HOME_ADDRESS, PHONE, EMAIL, SPECIAL_NEED);
    }
}
