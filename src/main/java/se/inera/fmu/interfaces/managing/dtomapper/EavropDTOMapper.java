package se.inera.fmu.interfaces.managing.dtomapper;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class EavropDTOMapper implements Mapper{

	protected static final String DEFAULT_COLOR = "fmu-table-color-inactive";
	protected static final String DANGER_COLOR = "bg-danger";
	protected static final String WARNING_COLOR = "bg-warning";
	protected static final String SUCCESS_COLOR = "bg-success";
	
	@Override
	public EavropDTO map(Eavrop eavrop, EavropDTO dto) {
		dto.setArendeId(eavrop.getArendeId().toString());
		dto.setEavropId(eavrop.getEavropId().getId());
		dto.setStatus(eavrop.getStatus());
		dto.setUtredningType(eavrop.getUtredningType().name());
		dto.setCreationTime(eavrop.getCreatedDate() != null ? eavrop.getCreatedDate().getMillis() : null);
		dto.setBestallareOrganisation(eavrop.getBestallaradministrator() != null ? 
				eavrop.getBestallaradministrator().getOrganisation() : null);
		dto.setBestallareEnhet(eavrop.getBestallaradministrator() != null ? 
				eavrop.getBestallaradministrator().getUnit() : null);
		dto.setMottagarenOrganisation(eavrop.getLandsting() != null ?
				eavrop.getLandsting().getName() : null);
		
		Vardgivarenhet currentAssignedEnhet = eavrop.getCurrentAssignedVardgivarenhet();
		if(currentAssignedEnhet != null){
			dto.setUtforandeEnhet(currentAssignedEnhet.getUnitName());
			dto.setUtforandeOrganisation(currentAssignedEnhet.getVardgivare() != null ? currentAssignedEnhet.getVardgivare().getName() : null);
		}

		dto.setAssigningPerson(eavrop.getCurrentAssignment() != null ? eavrop.getCurrentAssignment().getAssigningPerson().getName() : null);

		return dto;
	}

	@Override
	public EavropDTO map(Eavrop eavrop) {
		return map(eavrop, new EavropDTO());
	}

}
