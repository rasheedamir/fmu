package se.inera.fmu.interfaces.managing.dtomapper;

import java.util.Random;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.invanare.Invanare;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class EavropDTOMapper {

	public EavropDTO mappToDTO(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		Invanare invanare = eavrop.getInvanare();
		Address address = invanare.getHomeAddress();
		String devstatus = "In progress";
		
		DateTime dateTime = DateTime.now();
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setBestallareOrganisation(eavrop.getBestallaradministrator().getOrganistation())
		.setMottagarenOrganisation(devstatus)
		.setEnhet(devstatus)
		.setUtredare(devstatus)
		.setAntalDagarEfterForfragan(getRandomInt(0, 100))
		.setCreationTime(dateTime.plusDays(getRandomInt(0, 1000)))
		.setPatientCity(address.getCity())
		.setUtredningType(eavrop.getUtredningType())
		.setStatus(devstatus)
		.setRowColor(getRandomHexString(4));
		
		return dto;
	}
	
	private int getRandomInt(int i, int j) {
		Random r = new Random();
		return r.nextInt(j - i) + i;
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
