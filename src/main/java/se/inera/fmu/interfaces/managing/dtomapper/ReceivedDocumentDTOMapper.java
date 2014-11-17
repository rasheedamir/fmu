package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.interfaces.managing.rest.dto.ReceivedDocumentDTO;

public class ReceivedDocumentDTOMapper {
	public ReceivedDocumentDTO map(ReceivedDocument doc){
		ReceivedDocumentDTO dto = new ReceivedDocumentDTO();
		dto.setName(doc.getDocumentName());
		dto.setRegBy(String.format("%s, %s", doc.getPerson().getName(), doc.getPerson().getOrganisation()));
		dto.setRegDate(doc.getDocumentDateTime().getMillis());
		return dto;
	}
}	
