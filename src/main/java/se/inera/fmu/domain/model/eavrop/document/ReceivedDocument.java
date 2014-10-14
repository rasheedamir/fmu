package se.inera.fmu.domain.model.eavrop.document;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("RECEIVED")
@ToString
public class ReceivedDocument extends Document{


    //~ Constructors ===================================================================================================
    
	ReceivedDocument(){
		//Needed by Hibernate
	}
	
	public ReceivedDocument(final DocumentOriginType documentOriginType, final String documentName, final Person person){
		super(documentOriginType, documentName, person);
	}

	public ReceivedDocument(final DocumentOriginType documentOriginType, final LocalDateTime documentDateTime, final String documentName, final Person person){
		super(documentOriginType, documentDateTime, documentName, person);
	}

	
    //~ Property Methods ===============================================================================================
	
	//~ Other Methods ==================================================================================================

}
