package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("REQUEST")
@ToString
public class IntygComplementRequestInformation  extends IntygInformation implements Comparable<IntygComplementRequestInformation>{

	public IntygComplementRequestInformation(){
        //Needed by hibernate
    }

	public IntygComplementRequestInformation(
			DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}
	@Override
	public int compareTo(IntygComplementRequestInformation  other) {
	        return this.getInformationTimestamp().compareTo(other.getInformationTimestamp());
	}
	
	@Override
	public EavropEventDTO getAsEavropEvent() {
		return (this.getPerson()!=null)?
			new EavropEventDTO(EavropEventDTOType.INTYG_COMPLEMENT_REQUEST,this.getInformationTimestamp(),null, null, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit()):
			new EavropEventDTO(EavropEventDTOType.INTYG_COMPLEMENT_REQUEST,this.getInformationTimestamp(),null, null, null, null, null, null);
	}
}
