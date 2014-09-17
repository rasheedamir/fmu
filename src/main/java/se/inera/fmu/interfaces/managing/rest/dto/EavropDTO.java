package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.UtredningType;

/**
 * Created by Rasheed on 8/25/14.
 */
@ToString
public class EavropDTO {

    private String arendeId;

    private String utredningType;
    
    private String bestallareOrganisation;
    
    private String enhet;
    
    private DateTime creationTime;

    private String patientCity;

    private String mottagarenOrganisation;
    
    private String utredare;
    
    private String status;
    
    private int antalDagarEfterForfragan;

	private String color;

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

	public String getPatientCity() {
		return patientCity;
	}

	public EavropDTO setPatientCity(String patientCity) {
		this.patientCity = patientCity;
		return this;
	}

	public EavropDTO setRowColor(String randomHexString) {
		this.setColor(randomHexString);
		return this;
	}

	public String getColor() {
		return color;
	}

	public EavropDTO setColor(String color) {
		this.color = color;
		return this;
	}

	public String getBestallareOrganisation() {
		return bestallareOrganisation;
	}

	public EavropDTO setBestallareOrganisation(String bestallareOrganisation) {
		this.bestallareOrganisation = bestallareOrganisation;
		return this;
	}

	public String getEnhet() {
		return enhet;
	}

	public EavropDTO setEnhet(String enhet) {
		this.enhet = enhet;
		return this;
	}

	public String getMottagarenOrganisation() {
		return mottagarenOrganisation;
	}

	public EavropDTO setMottagarenOrganisation(String mottagarenOrganisation) {
		this.mottagarenOrganisation = mottagarenOrganisation;
		return this;
	}

	public String getUtredare() {
		return utredare;
	}

	public EavropDTO setUtredare(String utredare) {
		this.utredare = utredare;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public EavropDTO setStatus(String status) {
		this.status = status;
		return this;
	}

	public int getAntalDagarEfterForfragan() {
		return antalDagarEfterForfragan;
	}

	public EavropDTO setAntalDagarEfterForfragan(int antalDagarEfterForfragan) {
		this.antalDagarEfterForfragan = antalDagarEfterForfragan;
		return this;
	}
}
