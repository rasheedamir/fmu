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

import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.person.Person;



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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="REQUEST_NOTE_ID", nullable = true)
	private Note requestNote;
	
    //~ Constructors ===================================================================================================
    
	RequestedDocument(){
		//Needed by Hibernate
	}
	
	
	public RequestedDocument(final String documentName, final Person person, final Note requestNote){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notEmpty(documentName, "Document name may not be blank");
    	this.setDocumentDateTime(new DateTime());
    	this.setDocumentName(documentName);
    	this.setPerson(person);
    	this.setRequestNote(requestNote);
	}

	public RequestedDocument(final DateTime documentDateTime, final String documentName, final Person person, final Note requestNote){
    	this(documentName, person, requestNote);
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

	public Note getRequestNote() {
		return requestNote;
	}


	private void setRequestNote(Note requestNote) {
		this.requestNote = requestNote;
	}
	
    //~ Property Methods ===============================================================================================
	
	//~ Other Methods ==================================================================================================

}
