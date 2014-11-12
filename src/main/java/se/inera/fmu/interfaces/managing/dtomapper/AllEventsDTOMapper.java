package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;

public class AllEventsDTOMapper {
	public AllEventsDTO map(Eavrop eavrop){
		AllEventsDTO result = new AllEventsDTO();
		result.setDateInquiry(eavrop.getCreatedDate().getMillis());
		result.setDocumentsSent(eavrop.getDateTimeDocumentsSentFromBestallare().getMillis());
		result.setInvestigationNoDays(eavrop.getNoOfAssesmentDays());
		result.setReqAmendmentsDocuments(eavrop.getRequestedDocuments().size());
		result.setAcceptanceDate(eavrop.getCurrentAssignment().getAssignmentResponseDateTime().getMillis());
		result.setCertSentToOrdererDate(eavrop.getIntygSignedDateTime().getMillis());
		result.setNoDeviations(eavrop.getNumberOfDeviationsOnEavrop());
		result.setReqAmendmentsInvestigation(eavrop.getNoOfIntygComplementRequests());
		return result;
	}
}
