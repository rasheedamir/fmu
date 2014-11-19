package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.person.Person;

@Entity
@DiscriminatorValue("APPROVED")
@ToString
public class IntygApprovedInformation extends IntygInformation{

	public IntygApprovedInformation(){
        //Needed by hibernate
    }
	
	public IntygApprovedInformation(DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}
	
	@Override
	public EavropEventDTO getAsEavropEvent() {
		return (this.getPerson()!=null)?
			new EavropEventDTO(EavropEventDTOType.INTYG_APPROVED,this.getInformationTimestamp(),null, null, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit()):
			new EavropEventDTO(EavropEventDTOType.INTYG_APPROVED,this.getInformationTimestamp(),null, null, null, null, null, null);
	}

}