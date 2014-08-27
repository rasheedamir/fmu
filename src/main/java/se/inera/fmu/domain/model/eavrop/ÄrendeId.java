package se.inera.fmu.domain.model.eavrop;

import lombok.ToString;
import org.apache.commons.lang3.Validate;
import se.inera.fmu.domain.shared.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Rasheed on 7/7/14.
 *
 * A key that uniquely identifies a particular Eavrop between Inera & FK Systems.
 *
 */
@ToString
@Embeddable
public final class ÄrendeId implements ValueObject<ÄrendeId> {

    //~ Instance fields ================================================================================================

    @Column(name = "ärende_id", nullable = false, updatable = false, unique = true)
    @NotNull
    @Size(max = 24)
    private String id;

    //~ Constructors ===================================================================================================

    ÄrendeId() {
        // Needed by Hibernate
    }

    /**
     * Constructor.
     *
     * @param id Id string.
     */
    public ÄrendeId(final String id) {
        Validate.notNull(id);
        this.id = id;
    }

    //~ Other Methods ==================================================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ÄrendeId other = (ÄrendeId) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean sameValueAs(ÄrendeId other) {
        return other != null && this.id.equals(other.id);
    }

}
