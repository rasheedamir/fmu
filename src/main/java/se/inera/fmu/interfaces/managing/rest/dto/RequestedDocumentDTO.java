package se.inera.fmu.interfaces.managing.rest.dto;

public class RequestedDocumentDTO {
	private String name;
	private String sentBy;
	private Long sentDate;
	private String comment;
	private String sentTo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
	public Long getSentDate() {
		return sentDate;
	}
	public void setSentDate(Long sentDate) {
		this.sentDate = sentDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
}
