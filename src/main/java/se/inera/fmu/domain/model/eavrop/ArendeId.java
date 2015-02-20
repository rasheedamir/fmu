package se.inera.fmu.domain.model.eavrop;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import se.inera.fmu.domain.shared.ValueObject;

/**
 * 
 * A key that uniquely identifies a particular Eavrop between Inera & FK Systems.
 * The ArendeId is owned by the customer.
 */
@Embeddable
public final class ArendeId implements ValueObject<ArendeId> {

	private static final long serialVersionUID = -1L;
	
	//~ Instance fields ================================================================================================

	@Column(name = "ARENDE_ID", nullable = false, updatable = false, unique = true, columnDefinition="char(12)")
    @NotNull
    //TODO Should we really limit the size of a business key that we don't own ourselves. If so then we could also add the ' length = 24' attribute to the @Column 
    @Size(max = 12)
    private String arendeId;

    //~ Constructors ===================================================================================================

    ArendeId() {
        // Needed by Hibernate
    }

    /**
     * Constructor.
     *
     * @param arendeId Id string.
     */
    public ArendeId(final String arendeId) {
    	validate(arendeId); 
        this.arendeId = arendeId;
    }

    //~ Other Methods ==================================================================================================

    private void validate(String arendeIdStr){
    	Validate.notNull(arendeIdStr);
    	if(!NumberUtils.isDigits(arendeIdStr) || !(arendeIdStr.length() == 12)){
        	throw new IllegalArgumentException(String.format("Invalid ÄrendeId: %s, an ÄrendeId need to be a 12 character numeric String", arendeIdStr));
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }
        return sameValueAs((ArendeId) o);
    }

    @Override
    public int hashCode() {
        return arendeId.hashCode();
    }

    @Override
    public boolean sameValueAs(ArendeId other) {
        return other != null && this.arendeId.equals(other.arendeId);
    }
    
    @Override
    public String toString() {
    	return arendeId;
    }
}
