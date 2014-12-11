package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;

public class AllEventsDTOMapper {
	public AllEventsDTO map(Eavrop eavrop){
		AllEventsDTO result = new AllEventsDTO();
		result.setDateInquiry(eavrop.getCreatedDate() != null ? eavrop.getCreatedDate().getMillis(): null);
		result.setDocumentsSent(eavrop.getDateTimeDocumentsSentFromBestallare() != null ? eavrop.getDateTimeDocumentsSentFromBestallare().getMillis() : null);
		result.setInvestigationNoDays(eavrop.getNoOfAssesmentDays());
		result.setReqAmendmentsDocuments(eavrop.getRequestedDocuments() != null ? eavrop.getRequestedDocuments().size() : null);
		result.setAcceptanceDate(eavrop.getCurrentAssignment() != null && eavrop.getCurrentAssignment().getAssignmentResponseDateTime() != null ?
				eavrop.getCurrentAssignment().getAssignmentResponseDateTime().getMillis() : null);
		result.setCertSentToOrdererDate(eavrop.getIntygSentDateTime() != null ? eavrop.getIntygSentDateTime().getMillis() : null);
		result.setNoDeviations(eavrop.getNumberOfDeviationsOnEavrop());
		result.setReqAmendmentsInvestigation(eavrop.getNoOfIntygComplementRequests());
		
		
		return result;
	}
}
