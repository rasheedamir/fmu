package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.interfaces.managing.rest.dto.PatientDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PatientDTO.Details;

public class PatientDTOMapper {
	public PatientDTO map(Eavrop eavrop, boolean detailed){
		PatientDTO dto = new PatientDTO();
		Invanare invanare = eavrop.getInvanare();
		
		dto.setDobYear(invanare.getPersonalNumber().getPersonalNumber().substring(0, 4));
		dto.setGender(invanare.getGender().toString());
		dto.setInitials(invanare.getName().getInitials());
		dto.setResidenceCity(invanare.getHomeAddress() != null ? invanare.getHomeAddress().getCity(): null);
		
		if(detailed){
			Details details = new Details();
			details.setName(invanare.getName().getFullName());
			details.setPhone(invanare.getPhone());
			details.setSpecialNeeds(invanare.getSpecialNeeds());
			details.setSocSecNo(invanare.getPersonalNumber().getPersonalNumber());
			PriorMedicalExamination priorMedicalExamination = eavrop.getPriorMedicalExamination();
			
			if(priorMedicalExamination != null){
				details.setPrevAssesment(priorMedicalExamination.getExaminedAt());
				details.setSjukskrivandeVk(priorMedicalExamination.getMedicalLeaveIssuedAt());
				details.setSjukskrivandeLakare(priorMedicalExamination.getMedicalLeaveIssuedBy().getName());
			}
			dto.setDetails(details);
		}
		
		return dto;
	}
}
