package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.ToString;
import se.inera.fmu.domain.model.eavrop.EavropStateType;
import se.inera.fmu.domain.model.eavrop.UtredningType;

/**
 * Created by Rasheed on 8/25/14.
 */
@ToString
public class EavropDTO {

    private String arendeId;
    
    private String eavropId;

    private String utredningType;
    
    private String bestallareEnhet;
    
    private String bestallareOrganisation;
    
    private long creationTime;

    private String patientCity;

    private String mottagarenOrganisation;
    
    private EavropStateType status;
    
    private Integer antalDagarEfterForfragan;
    
    private Integer avikelser;

	private String color;
	
	private String startDate
	
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

    public long getCreationTime() {
        return creationTime;
    }

    public EavropDTO setCreationTime(long l) {
        this.creationTime = l;
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

	public String getMottagarenOrganisation() {
		return mottagarenOrganisation;
	}

	public EavropDTO setLeverantorOrganisation(String mottagarenOrganisation) {
		this.mottagarenOrganisation = mottagarenOrganisation;
		return this;
	}

	public EavropStateType getStatus() {
		return status;
	}

	public EavropDTO setStatus(EavropStateType eavropStateType) {
		this.status = eavropStateType;
		return this;
	}

	public Integer getAntalDagarEfterForfragan() {
		return antalDagarEfterForfragan;
	}

	public EavropDTO setAntalDagarEfterForfragan(Integer antalDagarEfterForfragan) {
		this.antalDagarEfterForfragan = antalDagarEfterForfragan;
		return this;
	}
	
	public EavropDTO setBestallareEnhet(String bestallareEnhet) {
		this.bestallareEnhet = bestallareEnhet;
		return this;
	}
	
	public String getBestallareEnhet() {
		return bestallareEnhet;
	}

	public EavropDTO setAvikelser(Integer numberOfDeviationsOnEavrop) {
		this.avikelser = numberOfDeviationsOnEavrop;
		return this;
	}
	
	public Integer getAvikelser() {
		return this.avikelser;
	}
	
	public EavropDTO setMottagarenOrganisation(String mottagarenOrganisation) {
		this.mottagarenOrganisation = mottagarenOrganisation;
		return this;
	}
	
	public EavropDTO setEavropId(String eavropId) {
		this.eavropId = eavropId;
		return this;
	}
	
	public String getEavropId() {
		return eavropId;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public EavropDTO setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}
}
