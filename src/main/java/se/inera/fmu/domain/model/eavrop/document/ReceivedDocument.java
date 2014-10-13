package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.URL;

import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@DiscriminatorValue("RECEIVED")
@ToString
public class ReceivedDocument extends Document{


    //~ Constructors ===================================================================================================
    
	ReceivedDocument(){
		//Needed by Hibernate
	}
	
	public ReceivedDocument(final String documentName, final Person person){
		super(documentName, person);
	}

    //~ Property Methods ===============================================================================================
	
	//~ Other Methods ==================================================================================================

}
