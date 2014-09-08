package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Name;
import se.inera.fmu.domain.model.patient.Patient;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class EavropDTOMapper {

	public EavropDTO mappToDTO(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		Patient patient = eavrop.getPatient();
		Address address = patient.getHomeAddress();
		Name name = patient.getName();
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setCreationTime(eavrop.getCreatedDate())
		.setFirstName(name.getFirstName())
		.setInitials(name.getInitials())
		.setLastName(name.getLastName())
		.setMiddleName(name.getMiddleName())
		.setPatientAddress1(address.getAddress1())
		.setPatientAddress2(address.getAddress2())
		.setPatientCity(address.getCity())
		.setPatientCountry(address.getCountry())
		.setPatientEmail(patient.getEmail())
		.setPatientGender(patient.getGender())
		.setPatientPersonalNumber(patient.getPersonalNumber())
		.setPatientPostalCode(address.getPostalCode())
		.setPatientState(address.getState())
		.setUtredningType(eavrop.getUtredningType());
		
		return dto;
	}
}
