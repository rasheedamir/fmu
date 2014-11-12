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

	private Long creationTime;

	private String patientCity;

	private String mottagarenOrganisation;

	private EavropStateType status;

	private Integer antalDagarEfterForfragan;

	private Integer avikelser;

	private String color;

	private Long startDate;

	private Integer nrOfDaysSinceStart;

	private Integer dagarFromStartToAccepted;

	private String utredareOrganisation;

	private Long dateIntygDelivered;

	private Long eavropApprovalDatetime;

	private Boolean isEavropApprovedForPayment;

	private Integer totalCompletionDays;

	private String ansvarigUtredare;

	private Boolean isCompleted;

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

	public Long getCreationTime() {
		return creationTime;
	}

	public EavropDTO setCreationTime(Long l) {
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

	public EavropDTO setAntalDagarEfterForfragan(
			Integer antalDagarEfterForfragan) {
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

	public Long getStartDate() {
		return startDate;
	}

	public EavropDTO setStartDate(Long date) {
		this.startDate = date;
		return this;
	}

	public EavropDTO setNrOfDaysAfterStart(
			Integer numberOfDaysUsedDuringAssessment) {
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

	public EavropDTO setIntygDeliveredDate(Long deliveredDate) {
		this.dateIntygDelivered = deliveredDate;
		return this;
	}

	public Long getDateIntygDelivered() {
		return dateIntygDelivered;
	}

	public Boolean isEavropApprovedForPayment() {
		return isEavropApprovedForPayment;
	}

	public EavropDTO setEavropApprovedForPayment(
			Boolean isEavropApprovedForPayment) {
		this.isEavropApprovedForPayment = isEavropApprovedForPayment;
		return this;
	}

	public Long getEavropApprovalDatetime() {
		return eavropApprovalDatetime;
	}

	public EavropDTO setEavropApprovalDatetime(Long eavropApprovalDatetime) {
		this.eavropApprovalDatetime = eavropApprovalDatetime;
		return this;
	}

	public EavropDTO setAntalDagarFromKompleteringBegarToBestallaren(
			Integer noOfDaysUsedForLastComplementRequest) {
		this.totalCompletionDays = noOfDaysUsedForLastComplementRequest;
		return this;
	}
	
	public Integer getTotalCompletionDays() {
		return totalCompletionDays;
	}
	
	public Boolean getIsEavropApprovedForPayment() {
		return isEavropApprovedForPayment;
	}

	public EavropDTO setUtredareAnsvarigNamn(String name) {
		this.ansvarigUtredare = name;
		return this;
	}
	
	public String getAnsvarigUtredare() {
		return ansvarigUtredare;
	}

	public EavropDTO setIsIntygComplete(Boolean approved) {
		this.isCompleted = approved;
		return this;
	}
	
	public Boolean getIsCompleted() {
		return isCompleted;
	}
}
