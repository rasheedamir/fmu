package se.inera.fmu.interfaces.managing.dtomapper;

import java.util.Random;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class DTOMapper {

	public EavropDTO mapToOverviewDTO(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setEavropId(eavrop.getEavropId().getId())
		.setBestallareOrganisation(eavrop.getBestallaradministrator().getOrganisation())
		.setLeverantorOrganisation(eavrop.getLandsting().getName())
		.setAntalDagarEfterForfragan(eavrop.getNumberOfDaysUsedDuringAssessment())
		.setCreationTime(eavrop.getCreatedDate().getMillis())
		.setPatientCity(eavrop.getInvanare().getHomeAddress().getCity())
		.setUtredningType(eavrop.getUtredningType())
		.setStatus(eavrop.getStatus())
		.setBestallareEnhet(eavrop.getBestallaradministrator().getUnit())
		.setAvikelser(eavrop.getNumberOfDeviationsOnEavrop())
		.setRowColor(getRandomHexString(4));
		
		return dto;
	}

	// Random color string
	private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return "#10" + sb.toString().substring(0, numchars);
    }
}
