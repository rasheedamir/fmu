package se.inera.fmu.interfaces.managing.rest.dto;

public class OrderDTO {
	private String arendeId;
	private String type;
	private String ordererOrganization;
	private String ordererUnit;
	private Long inquiryDate;
	private String patientResidence;
	private String investigatorOrganization;
	private String investigatorUnit;
	private Long acceptanceDate;
	private String interpreterLang;
	private String valAvInriktning;
	private String additionalInfo;
	private String ordererName;
	private String ordererPhone;
	private String ordererEmail;
	
	public String getArendeId() {
		return arendeId;
	}
	public void setArendeId(String arendeId) {
		this.arendeId = arendeId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrdererOrganization() {
		return ordererOrganization;
	}
	public void setOrdererOrganization(String ordererOrganization) {
		this.ordererOrganization = ordererOrganization;
	}
	public String getOrdererUnit() {
		return ordererUnit;
	}
	public void setOrdererUnit(String ordererUnit) {
		this.ordererUnit = ordererUnit;
	}
	public Long getInquiryDate() {
		return inquiryDate;
	}
	public void setInquiryDate(Long inquiryDate) {
		this.inquiryDate = inquiryDate;
	}
	public String getPatientResidence() {
		return patientResidence;
	}
	public void setPatientResidence(String patientResidence) {
		this.patientResidence = patientResidence;
	}
	public String getInvestigatorOrganization() {
		return investigatorOrganization;
	}
	public void setInvestigatorOrganization(String investigatorOrganization) {
		this.investigatorOrganization = investigatorOrganization;
	}
	public String getInvestigatorUnit() {
		return investigatorUnit;
	}
	public void setInvestigatorUnit(String investigatorUnit) {
		this.investigatorUnit = investigatorUnit;
	}
	public Long getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Long acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public String getInterpreterLang() {
		return interpreterLang;
	}
	public void setInterpreterLang(String interpreterLang) {
		this.interpreterLang = interpreterLang;
	}
	public String getValAvInriktning() {
		return valAvInriktning;
	}
	public void setValAvInriktning(String valAvInriktning) {
		this.valAvInriktning = valAvInriktning;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getOrdererName() {
		return ordererName;
	}
	public void setOrdererName(String ordererName) {
		this.ordererName = ordererName;
	}
	public String getOrdererPhone() {
		return ordererPhone;
	}
	public void setOrdererPhone(String ordererPhone) {
		this.ordererPhone = ordererPhone;
	}
	public String getOrdererEmail() {
		return ordererEmail;
	}
	public void setOrdererEmail(String ordererEmail) {
		this.ordererEmail = ordererEmail;
	}
}
