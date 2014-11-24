package se.inera.fmu.interfaces.managing.rest.dto;

import java.util.List;

public class EavropPageDTO {
	private List<EavropDTO> eavrops;
	private long totalElements;
	
	public List<EavropDTO> getEavrops() {
		return eavrops;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public EavropPageDTO setEavrops(List<EavropDTO> data) {
		this.eavrops = data;
		return this;
	}
	
	public EavropPageDTO setTotalElements(long l) {
		this.totalElements = l;
		return this;
	}
}
