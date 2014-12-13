package se.inera.fmu.domain.model.landsting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import se.inera.fmu.domain.shared.ValueObject;

@Embeddable
public class LandstingCode implements ValueObject<LandstingCode>, Serializable {
	
	//~ Instance fields ================================================================================================

    @Column(name = "LANDSTING_CODE", nullable = false, updatable = false, unique = true)
    @NotNull
    private int code;

    //~ Constructors ===================================================================================================

	LandstingCode() {
	    // Needed by Hibernate
	}

	public LandstingCode(final int code) throws IllegalArgumentException {
		setCode(code);
	}
	
    //~ Property Methods ===============================================================================================

	public int getCode(){
		return this.code;
	}

	private void setCode(final int code) {
		this.code = code;
	}

    //~ Other Methods ==================================================================================================


	@Override
	public String toString() {
		return "" + getCode();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (o == null || getClass() != o.getClass()){
        	return false;
        }
        LandstingCode other = (LandstingCode) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean sameValueAs(LandstingCode other) {
        return other != null && this.getCode() == other.getCode();
    }

}
