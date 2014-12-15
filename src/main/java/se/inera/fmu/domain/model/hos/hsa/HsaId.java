package se.inera.fmu.domain.model.hos.hsa;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import se.inera.fmu.domain.shared.ValueObject;

@Embeddable
public class HsaId implements ValueObject<HsaId>, Serializable {
	
	static final String HSA_ID_REGEX = "^SE(16)?(\\d{6})(\\d{4})-.*";
	private static final Pattern HSA_ID_PATTERN = Pattern.compile(HSA_ID_REGEX);

	
	//~ Instance fields ================================================================================================

    @Column(name = "HSA_ID", nullable = false, updatable = false, unique = true)
    @NotNull
    private String id;

    //~ Constructors ===================================================================================================

	HsaId() {
	    // Needed by Hibernate
	}

	public HsaId(final String hsaId) throws IllegalArgumentException {
//		if (!isValid(hsaId)) {
//			throw new IllegalArgumentException("Invalid hsaId");
//		}
		this.id = hsaId;
	}

	
    //~ Property Methods ===============================================================================================

	public String getHsaId(){
		return id;
	}

	private void setHsaId(String hsaId) {
		this.id = hsaId;
	}

    //~ Other Methods ==================================================================================================

	public static boolean isValid(String hsaId) {
		return hsaId.matches(HSA_ID_REGEX);
	}

	@Override
	public String toString() {
		return id;
	}

	public String extractOrgNo() {
		Matcher matcher = HSA_ID_PATTERN.matcher(id);
		if (!matcher.find()) {
			throw new IllegalStateException("Illegal hsa id");
		}
		return matcher.group(2) + "-" + matcher.group(3);
	}
	
    @Override
    public boolean equals(Object other) {
        if (this == other){
        	return true;
        } 
        if (other == null || getClass() != other.getClass()){
        	return false;
        } 
        return sameValueAs((HsaId)other);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean sameValueAs(HsaId other) {
        return other != null && this.id.equals(other.id);
    }

}
