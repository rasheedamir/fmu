package se.inera.fmu.domain.model.eavrop.intyg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.eavrop.EavropEventDTO;
import se.inera.fmu.domain.model.eavrop.EavropEventDTOType;
import se.inera.fmu.domain.model.person.Person;

@Entity
@DiscriminatorValue("SENT")
@ToString
public class IntygSentInformation extends IntygInformation implements Comparable<IntygSentInformation> {
	
	public IntygSentInformation(){
        //Needed by hibernate
    }

	public IntygSentInformation(DateTime informationTimestamp, Person person) {
		super(informationTimestamp, person);
	}

	@Override
	public int compareTo(IntygSentInformation  other) {
	        return this.getInformationTimestamp().compareTo(other.getInformationTimestamp());
	}

	@Override
	public EavropEventDTO getAsEavropEvent() {
		return (this.getPerson()!=null)?
			new EavropEventDTO(EavropEventDTOType.INTYG_SENT,this.getInformationTimestamp(),null, null, getPerson().getName(), getPerson().getRole(), getPerson().getOrganisation(), getPerson().getUnit()):
			new EavropEventDTO(EavropEventDTOType.INTYG_SENT,this.getInformationTimestamp(),null, null, null, null, null, null);
	}
}
