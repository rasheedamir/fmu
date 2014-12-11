package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;

public class OrderDTOMapper {
	public OrderDTO map(Eavrop eavrop) {
		OrderDTO dto = new OrderDTO();

		dto.setArendeId(eavrop.getArendeId().toString());
		dto.setType(eavrop.getUtredningType().toString());
		dto.setInquiryDate(eavrop.getCreatedDate().getMillis());
		dto.setPatientResidence(eavrop.getInvanare().getHomeAddress().getCity());
		
		Vardgivarenhet currentAssigmentEnhet = eavrop.getCurrentAssignedVardgivarenhet();
		if(currentAssigmentEnhet != null){
			dto.setInvestigatorOrganization(currentAssigmentEnhet.getVardgivare().getName());
			dto.setInvestigatorUnit(currentAssigmentEnhet.getUnitName());
		}
		
		
		dto.setAcceptanceDate(eavrop.getCurrentAssignment() != null ?
				eavrop.getCurrentAssignment().getAssignmentResponseDateTime() != null ? 
						eavrop.getCurrentAssignment().getAssignmentResponseDateTime().getMillis() : null : null);
		dto.setInterpreterLang(eavrop.getIterpreterDescription());
		dto.setValAvInriktning(eavrop.getUtredningFocus());
		dto.setAdditionalInfo(eavrop.getAdditionalInformation());

		Bestallaradministrator bestallare = eavrop.getBestallaradministrator();
		if(bestallare != null){
			dto.setOrdererOrganization(eavrop.getBestallaradministrator().getOrganisation());
			dto.setOrdererUnit(eavrop.getBestallaradministrator().getUnit());
			dto.setOrdererName(eavrop.getBestallaradministrator().getName());
			dto.setOrdererEmail(eavrop.getBestallaradministrator().getEmail());
			dto.setOrdererPhone(eavrop.getBestallaradministrator().getPhone());
		}

		return dto;
	}
}
