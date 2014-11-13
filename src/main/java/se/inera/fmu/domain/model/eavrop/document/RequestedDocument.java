package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.IEntity;



@Entity
@Table(name = "T_REQ_DOCUMENT")
@ToString
public class RequestedDocument {

    // database primary key
    @Id
    @Column(name = "DOCUMENT_ID", updatable = false, nullable = false)
    private String id;
    
	@NotNull
    @Column(name = "DOCUMENT_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime documentDateTime;

    
    @Column(name = "NAME", updatable = false,  nullable = false)
    private String documentName;
    
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;
	
    //~ Constructors ===================================================================================================
    
	RequestedDocument(){
		//Needed by Hibernate
	}
	
	
	public RequestedDocument(final String documentName, final Person person){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(documentName);
    	this.setDocumentDateTime(new DateTime());
    	this.setDocumentName(documentName);
    	this.setPerson(person);
	}

	public RequestedDocument(final DateTime documentDateTime, final String documentName, final Person person){
    	this(documentName, person);
    	if(this.documentDateTime!=null){
    		this.setDocumentDateTime(documentDateTime);
    	}
   }


	public String getId() {
		return id;
	}


	private void setId(String id) {
		this.id = id;
	}


	public DateTime getDocumentDateTime() {
		return documentDateTime;
	}


	private void setDocumentDateTime(DateTime documentDateTime) {
		this.documentDateTime = documentDateTime;
	}


	public String getDocumentName() {
		return documentName;
	}


	private void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	public Person getPerson() {
		return person;
	}


	private void setPerson(Person person) {
		this.person = person;
	}

    //~ Property Methods ===============================================================================================
	
	//~ Other Methods ==================================================================================================

}
