package se.inera.fmu.domain.model.landsting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import se.inera.fmu.domain.shared.ValueObject;

@Embeddable
public class LandstingId implements ValueObject<LandstingId>, Serializable {
	
	
	//~ Instance fields ================================================================================================

    @Column(name = "LANDSTING_ID", nullable = false, updatable = false, unique = true)
    @NotNull
    private int landstingId;

    //~ Constructors ===================================================================================================

	LandstingId() {
	    // Needed by Hibernate
	}

	public LandstingId(final int landstingId) throws IllegalArgumentException {
		setLandstingId(landstingId);
	}

	
    //~ Property Methods ===============================================================================================

	public int getLandstingId(){
		return this.landstingId;
	}

	private void setLandstingId(int landstingId) {
		this.landstingId = landstingId;
	}

    //~ Other Methods ==================================================================================================


	@Override
	public String toString() {
		return "" + getLandstingId();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LandstingId other = (LandstingId) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean sameValueAs(LandstingId other) {
        return other != null && this.getLandstingId() == other.getLandstingId();
    }

}
