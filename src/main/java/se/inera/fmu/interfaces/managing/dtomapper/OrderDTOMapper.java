package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;

public class OrderDTOMapper {
	public OrderDTO map(Eavrop eavrop){
		OrderDTO dto = new OrderDTO();
		dto.setArendeId(eavrop.getArendeId().toString());
		dto.setType(eavrop.getUtredningType().toString());
		dto.setOrdererOrganization(eavrop.getBestallaradministrator().getOrganisation());
		dto.setOrdererUnit(eavrop.getBestallaradministrator().getUnit());
		dto.setInquiryDate(eavrop.getCreatedDate().getMillis());
		dto.setPatientResidence(eavrop.getInvanare().getHomeAddress().getCity());
		dto.setInvestigatorOrganization(eavrop.getCurrentAssignedVardgivarenhet().getVardgivare().getName());
		dto.setInvestigatorUnit(eavrop.getCurrentAssignedVardgivarenhet().getUnitName());
		dto.setAcceptanceDate(eavrop.getCurrentAssignment().getAssignmentResponseDateTime().getMillis());
		return dto;
	}
}
