package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.URL;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DOCUMENT_TYPE")
@Table(name = "T_DOCUMENT")
@ToString
public abstract class Document  implements IEntity<Document>{

    // database primary key
    @Id
    @Column(name = "DOCUMENT_ID", updatable = false, nullable = false)
    private String id;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private DocumentOriginType documentType;
	
	@NotNull
    @Column(name = "DOCUMENT_DATETIME", nullable = false, updatable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime documentDateTime;

    
    @Column(name = "NAME", updatable = false,  nullable = false)
    private String name;
    
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;

    //~ Constructors ===================================================================================================
    
	Document(){
		//Needed by Hibernate
	}
	
	public Document(final DocumentOriginType documentType, final String documentName, final Person person){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(documentType);
    	Validate.notNull(documentName);
    	this.setDocumentDateTime(new LocalDateTime());
    	this.setDocumentName(documentName);
    	this.setPerson(person);
	}

	public Document(final DocumentOriginType documentType, final LocalDateTime documentDateTime, final String documentName, final Person person){
    	this(documentType, documentName, person);
    	if(documentDateTime!=null){
    		this.setDocumentDateTime(documentDateTime);
    	}
   }

	
    //~ Property Methods ===============================================================================================
	
	private DocumentOriginType getDocumentType() {
		return documentType;
	}

	private void setDocumentType(DocumentOriginType documentType) {
		this.documentType = documentType;
	}
	
	private LocalDateTime getDocumentDateTime() {
		return documentDateTime;
	}

	private void setDocumentDateTime(LocalDateTime documentDateTime) {
		this.documentDateTime = documentDateTime;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getDocumentNamn() {
		return name;
	}

	private void setDocumentName(String documentName) {
		this.name = documentName;
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}
	
	
	//~ Other Methods ==================================================================================================

	@Override
	public boolean sameIdentityAs(Document other) {
		return other != null && this.getId().equals(other.getId());
	}

	/**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Booking)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final Document other = (Document) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of tracking id.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
