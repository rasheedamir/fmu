package se.inera.fmu.domain.model.eavrop.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import lombok.ToString;

@Entity
@DiscriminatorValue("RECEIVED")
@ToString
public class ReceivedDocument extends Document{

	@Column(name = "EXTERNAL")
	private Boolean originExternal;
	
    //~ Constructors ===================================================================================================
    
	ReceivedDocument(){
		//Needed by Hibernate
	}
	
	public ReceivedDocument(final String documentName, final Person person, final Boolean isOriginExternal){
		super(documentName, person);
		originExternal = isOriginExternal;
	}

	public ReceivedDocument(final DateTime documentDateTime, final String documentName, final Person person, final Boolean isOriginExternal){
		super(documentDateTime, documentName, person);
		this.originExternal = isOriginExternal;
	}
	
    //~ Property Methods ===============================================================================================

	private void setOriginExternal(Boolean isOriginExternal){
		this.originExternal = isOriginExternal;
	}

	public boolean isDocumentOriginExternal(){
		if(this.originExternal != null){
			return this.originExternal;
		}
		return false;
	}
	
	//~ Other Methods ==================================================================================================
}
