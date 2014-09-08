package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.patient.Gender;
import se.inera.fmu.domain.model.patient.Initials;

/**
 * Created by Rasheed on 8/25/14.
 */
@ToString
public class EavropDTO {

    private String arendeId;

    private String utredningType;

    private DateTime creationTime;
    
    private String patientPersonalNumber;

    private Initials initials;

    private String firstName;

    private String middleName;

    private String lastName;

    private String patientGender;

    private String patientEmail;
    
    private String patientAddress1;

    private String patientAddress2;

    private String patientPostalCode;

    private String patientState;

    private String patientCity;

    private String patientCountry;

    public EavropDTO() {
    }

    public String getArendeId() {
        return arendeId;
    }

    public EavropDTO setArendeId(String arendeId) {
        this.arendeId = arendeId;
		return this;
    }

    public String getUtredningType() {
        return utredningType;
    }

    public EavropDTO setUtredningType(UtredningType utredningType) {
        this.utredningType = utredningType.toString();
		return this;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public EavropDTO setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
		return this;
    }

	public String getPatientPersonalNumber() {
		return patientPersonalNumber;
	}

	public EavropDTO setPatientPersonalNumber(String patientPerrsonalNumber) {
		this.patientPersonalNumber = patientPerrsonalNumber;
		return this;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public EavropDTO setPatientGender(Gender patientGender) {
		this.patientGender = patientGender.toString();
		return this;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public EavropDTO setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
		return this;
	}

	public String getPatientAddress1() {
		return patientAddress1;
	}

	public EavropDTO setPatientAddress1(String patientAddress1) {
		this.patientAddress1 = patientAddress1;
		return this;
	}

	public String getPatientAddress2() {
		return patientAddress2;
	}

	public EavropDTO setPatientAddress2(String patientAddress2) {
		this.patientAddress2 = patientAddress2;
		return this;
	}

	public String getPatientPostalCode() {
		return patientPostalCode;
	}

	public EavropDTO setPatientPostalCode(String patientPostalCode) {
		this.patientPostalCode = patientPostalCode;
		return this;
	}

	public String getPatientState() {
		return patientState;
	}

	public EavropDTO setPatientState(String patientState) {
		this.patientState = patientState;
		return this;
	}

	public String getPatientCity() {
		return patientCity;
	}

	public EavropDTO setPatientCity(String patientCity) {
		this.patientCity = patientCity;
		return this;
	}

	public String getPatientCountry() {
		return patientCountry;
	}

	public EavropDTO setPatientCountry(String patientCountry) {
		this.patientCountry = patientCountry;
		return this;
	}

	public Initials getInitials() {
		return initials;
	}

	public EavropDTO setInitials(Initials initials) {
		this.initials = initials;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public EavropDTO setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public EavropDTO setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public EavropDTO setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
}
