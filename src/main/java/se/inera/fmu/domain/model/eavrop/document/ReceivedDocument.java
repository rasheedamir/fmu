package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import se.inera.fmu.domain.model.person.Person;

@Entity
@Table(name = "T_REC_DOCUMENT")
@ToString
public class ReceivedDocument{

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
	
	@Column(name = "EXTERNAL")
	private Boolean originExternal;
	
    //~ Constructors ===================================================================================================
    
	ReceivedDocument(){
		//Needed by Hibernate
	}
	
	public ReceivedDocument(final String documentName, final Person person, final Boolean isOriginExternal){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(documentName);
    	this.setDocumentDateTime(new DateTime());
    	this.setDocumentName(documentName);
    	this.setPerson(person);
    	this.setOriginExternal(isOriginExternal);
	}

	public ReceivedDocument(final DateTime documentDateTime, final String documentName, final Person person, final Boolean isOriginExternal){
    	this(documentName, person, isOriginExternal);
    	if(this.documentDateTime!=null){
    		this.setDocumentDateTime(documentDateTime);
    	}

	}
	
    //~ Property Methods ===============================================================================================

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
