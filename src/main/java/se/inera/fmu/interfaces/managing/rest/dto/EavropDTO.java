package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.ToString;

import org.joda.time.LocalDate;

import se.inera.fmu.domain.model.eavrop.EavropApproval;
import se.inera.fmu.domain.model.eavrop.EavropCompensationApproval;
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
	
	private long startDate;

	private Integer nrOfDaysSinceStart;

	private Integer dagarFromStartToAccepted;

	private String utredareOrganisation;

	private long dateIntygDelivered;

	private long eavropApprovalDatetime;

	private boolean isEavropApprovedForPayment;
	
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
	
	public long getStartDate() {
		return startDate;
	}
	
	public EavropDTO setStartDate(LocalDate localDate) {
		this.startDate = localDate.toDateTimeAtCurrentTime().getMillis();
		return this;
	}

	public EavropDTO setNrOfDaysAfterStart(Integer numberOfDaysUsedDuringAssessment) {
		this.nrOfDaysSinceStart = numberOfDaysUsedDuringAssessment;
		return this;
	}
	
	public Integer getNrOfDaysSinceStart() {
		return nrOfDaysSinceStart;
	}

	public EavropDTO setAntalDagarFromStartToAccepted(Integer noOfAssesmentDays) {
		this.dagarFromStartToAccepted = noOfAssesmentDays;
		return this;
	}
	
	public Integer getDagarFromStartToAccepted() {
		return dagarFromStartToAccepted;
	}

	public EavropDTO setUtredareOrganisation(String name) {
		this.utredareOrganisation = name;
		return this;
	}
	
	public String getUtredareOrganisation() {
		return utredareOrganisation;
	}

	public EavropDTO setIntygDeliveredDate(long deliveredDate) {
		this.dateIntygDelivered = deliveredDate;
		return this;
	}
	
	public long getDateIntygDelivered() {
		return dateIntygDelivered;
	}

	public boolean isEavropApprovedForPayment() {
		return isEavropApprovedForPayment;
	}

	public EavropDTO setEavropApprovedForPayment(boolean isEavropApprovedForPayment) {
		this.isEavropApprovedForPayment = isEavropApprovedForPayment;
		return this;
	}

	public long getEavropApprovalDatetime() {
		return eavropApprovalDatetime;
	}

	public EavropDTO setEavropApprovalDatetime(long eavropApprovalDatetime) {
		this.eavropApprovalDatetime = eavropApprovalDatetime;
		return this;
	}
}
