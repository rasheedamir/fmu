package se.inera.fmu.interfaces.managing.rest.dto;

public class ReceivedDocumentDTO {
	private String name;
	private String regBy;
	private Long regDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegBy() {
		return regBy;
	}
	public void setRegBy(String regBy) {
		this.regBy = regBy;
	}
	public Long getRegDate() {
		return regDate;
	}
	public void setRegDate(Long regDate) {
		this.regDate = regDate;
	}
}
