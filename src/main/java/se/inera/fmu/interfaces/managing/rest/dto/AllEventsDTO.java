package se.inera.fmu.interfaces.managing.rest.dto;
/**
 * DTO for "Alla händelser"
 * @author kristoffer
 *
 */
public class AllEventsDTO {
	/**
	 * Förfrågan om utredning
	 */
	private Long dateInquiry;
	
	/**
	 * Handlingar översändes
	 */
	private Long documentsSent;
	
	/**
	 * Utredning antal dagar
	 */
	private Integer investigationNoDays;
	
	/**
	 * Begärda tillägg, handlingar
	 */
	private Integer reqAmendmentsDocuments;
	
	/**
	 * Acceptans av utredningen
	 */
	private Long acceptanceDate;
	
	/**
	 * Intyg skickat till beställaren
	 */
	private Long certSentToOrdererDate;
	
	/**
	 * Antal avvikelser
	 */
	private Integer noDeviations;
	
	/**
	 * Begärda kompletteringar till utredning
	 */
	private Integer reqAmendmentsInvestigation;

	public Long getDateInquiry() {
		return dateInquiry;
	}

	public void setDateInquiry(Long dateInquiry) {
		this.dateInquiry = dateInquiry;
	}

	public Long getDocumentsSent() {
		return documentsSent;
	}

	public void setDocumentsSent(Long documentsSent) {
		this.documentsSent = documentsSent;
	}

	public Integer getInvestigationNoDays() {
		return investigationNoDays;
	}

	public void setInvestigationNoDays(Integer investigationNoDays) {
		this.investigationNoDays = investigationNoDays;
	}

	public Integer getReqAmendmentsDocuments() {
		return reqAmendmentsDocuments;
	}

	public void setReqAmendmentsDocuments(Integer reqAmendmentsDocuments) {
		this.reqAmendmentsDocuments = reqAmendmentsDocuments;
	}

	public Long getAcceptanceDate() {
		return acceptanceDate;
	}

	public void setAcceptanceDate(Long acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	public Long getCertSentToOrdererDate() {
		return certSentToOrdererDate;
	}

	public void setCertSentToOrdererDate(Long certSentToOrdererDate) {
		this.certSentToOrdererDate = certSentToOrdererDate;
	}

	public Integer getNoDeviations() {
		return noDeviations;
	}

	public void setNoDeviations(Integer noDeviations) {
		this.noDeviations = noDeviations;
	}

	public Integer getReqAmendmentsInvestigation() {
		return reqAmendmentsInvestigation;
	}

	public void setReqAmendmentsInvestigation(Integer reqAmendmentsInvestigation) {
		this.reqAmendmentsInvestigation = reqAmendmentsInvestigation;
	}
}
