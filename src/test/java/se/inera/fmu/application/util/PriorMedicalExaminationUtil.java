package se.inera.fmu.application.util;

import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.person.HoSPerson;

public class PriorMedicalExaminationUtil {

	public static final String EXAMINED_AT = "Blommans häloscentral";
    public static final String MEDICAL_LEAVE_ISSUED_AT = "Karolinska institutet";
    public static final String NAME = "Jan Jansson";
    public static final String ROLE = "Läkare";
    public static final String ORGANISATION = "KSJH";
    public static final String UNIT = "Enheten";
    public static final String PHONE = "555-121212";
    public static final String EMAIL = "jan.janssonn@ksjh.se";

	public static PriorMedicalExamination createPriorMedicalExamination() {
		return new PriorMedicalExamination(EXAMINED_AT, MEDICAL_LEAVE_ISSUED_AT, new HoSPerson(null, NAME, ROLE, ORGANISATION, UNIT)); 
	}
}
