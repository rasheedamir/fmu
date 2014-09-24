package se.inera.fmu.domain.model.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;

import org.apache.commons.lang.Validate;

import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_DOCUMENT")
@ToString
public class Document extends AbstractBaseEntity implements IEntity<Document>{

    // database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    
    @Column(name = "NAME", updatable = false,  nullable = false)
    private String name;
	
    //~ Constructors ===================================================================================================
    
	Document(){
		//Needed by Hibernate
	}
	
	Document(final String documentName){
    	Validate.notNull(documentName);
    	this.setDocumentName(documentName);
	}

    //~ Property Methods ===============================================================================================
	
	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getDocumentNamn() {
		return name;
	}

	private void setDocumentName(String documentName) {
		this.name = documentName;
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
