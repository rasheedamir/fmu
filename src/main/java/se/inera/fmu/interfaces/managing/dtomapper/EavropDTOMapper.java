package se.inera.fmu.interfaces.managing.dtomapper;

import java.util.Random;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.patient.Address;
import se.inera.fmu.domain.model.patient.Patient;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;

public class EavropDTOMapper {

	public EavropDTO mappToDTO(Eavrop eavrop) {
		EavropDTO dto = new EavropDTO();
		Patient patient = eavrop.getPatient();
		Address address = patient.getHomeAddress();
		String devstatus = "In progress";
		
		dto.setArendeId(eavrop.getArendeId().toString())
		.setBestallareOrganisation(devstatus)
		.setMottagarenOrganisation(devstatus)
		.setEnhet(devstatus)
		.setUtredare(devstatus)
		.setAntalDagarEfterForfragan(getRandomInt(0, 100))
		.setCreationTime(DateTime.now())
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

        return "#20" + sb.toString().substring(0, numchars);
    }
}
