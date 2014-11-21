package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.List;

public class EavropPageDTO {
	private List<EavropBaseDTO> eavrops;
	private long totalElements;
	
	public List<EavropBaseDTO> getEavrops() {
		return eavrops;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public EavropPageDTO setEavrops(List<EavropBaseDTO> data) {
		this.eavrops = data;
		return this;
	}
	
	public EavropPageDTO setTotalElements(long l) {
		this.totalElements = l;
		return this;
	}
}
