package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.interfaces.managing.rest.dto.RequestedDocumentDTO;

public class RequestedDocumentDTOMapper {

	public RequestedDocumentDTO map(RequestedDocument doc, Eavrop eavrop) {
		RequestedDocumentDTO dto = new RequestedDocumentDTO();
		dto.setName(doc.getDocumentName());
		dto.setComment(doc.getRequestNote().getText());
		dto.setSentBy(String.format("%s, %s", doc.getPerson().getName(), doc.getPerson().getOrganisation()));
		dto.setSentTo(String.format("%s, %s", eavrop.getBestallaradministrator().getName(), eavrop.getBestallaradministrator().getOrganisation()));
		dto.setSentDate(doc.getDocumentDateTime().getMillis());
		return dto;
	}

}
