package se.inera.fmu.interfaces.managing.rest.dto;

public class VardgivarenhetDTO {
	private Long id;
	private String hsaId;
	private String unitName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHsaId() {
		return hsaId;
	}
	public void setHsaId(String hsaId) {
		this.hsaId = hsaId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
