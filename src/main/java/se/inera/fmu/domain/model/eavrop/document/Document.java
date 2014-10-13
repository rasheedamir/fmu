package se.inera.fmu.domain.model.eavrop.document;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
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
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DOCUMENT_TYPE")
@Table(name = "T_DOCUMENT")
@ToString
public abstract class Document extends AbstractBaseEntity implements IEntity<Document>{

    // database primary key
    @Id
    @Column(name = "DOCUMENT_ID", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "NAME", updatable = false,  nullable = false)
    private String name;
    
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERSON_ID")
	private Person person;

    //~ Constructors ===================================================================================================
    
	Document(){
		//Needed by Hibernate
	}
	
	public Document(final String documentName, final Person person){
    	this.setId(UUID.randomUUID().toString());
    	Validate.notNull(documentName);
    	this.setDocumentName(documentName);
    	this.setPerson(person);
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
