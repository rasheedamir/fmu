package se.inera.fmu.domain.model.eavrop.document;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.joda.time.LocalDateTime;

import lombok.ToString;
import se.inera.fmu.domain.model.person.Person;

@Entity
@DiscriminatorValue("REQUESTED")
@ToString
public class RequestedDocument extends Document{


    //~ Constructors ===================================================================================================
    
	RequestedDocument(){
		//Needed by Hibernate
	}
	
	public RequestedDocument(final String documentName, final Person person){
		super(DocumentOriginType.REQUESTED, documentName, person);
	}

	public RequestedDocument(final LocalDateTime documentDateTime, final String documentName, final Person person){
		super(DocumentOriginType.REQUESTED, documentDateTime, documentName, person);
	}
    //~ Property Methods ===============================================================================================
	
	//~ Other Methods ==================================================================================================

}
