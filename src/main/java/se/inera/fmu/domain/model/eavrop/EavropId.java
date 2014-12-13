package se.inera.fmu.domain.model.eavrop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import se.inera.fmu.domain.shared.ValueObject;

/**
 * Created by Rickard on 9/26/14.
 *
 */
@Embeddable
public final class EavropId implements ValueObject<EavropId>, Serializable {

    //~ Instance fields ================================================================================================

	@Column(name = "EAVROP_ID", unique=true, updatable = false, nullable = false, columnDefinition="char(36)")
    protected String id;

    //~ Constructors ===================================================================================================

    EavropId() {
        // Needed by hibernate
    }

    public EavropId(String id) {
        this.setId(id);
    }

    //~ Property Methods ===============================================================================================

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    //~ Other fields ===================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }

        EavropId other = (EavropId) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean sameValueAs(EavropId other) {
        return other != null && this.id.equals(other.id);
    }
   
    @Override
    public String toString() {
    	return getId();
    }
}
