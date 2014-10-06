package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.URL;

import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_DOCUMENT")
@ToString
public class Document extends AbstractBaseEntity implements IEntity<Document>{

    // database primary key
    @Id
    @Column(name = "DOCUMENT_ID", updatable = false, nullable = false)
    private String id;
    
    private DocumentType documentType;
    
    @Column(name = "NAME", updatable = false,  nullable = false)
    private String name;

    //Investigation is being performed, to check if documents can be made available from bestallare via url.  
//    @URL	
//    @Column(name = "URL")
//    private String url;

    //~ Constructors ===================================================================================================
    
	Document(){
		//Needed by Hibernate
	}
	
	Document(final String documentName, final DocumentType documentType){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(documentName);
    	this.setDocumentName(documentName);
    	Validate.notNull(documentType);
    	this.setDocumentType(documentType);
	}

    //~ Property Methods ===============================================================================================
	
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

	public DocumentType getDocumentType() {
		return documentType;
	}

	private void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
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
