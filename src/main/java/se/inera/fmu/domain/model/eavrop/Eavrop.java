package se.inera.fmu.domain.model.eavrop;

import lombok.ToString;
import org.apache.commons.lang3.Validate;
import se.inera.fmu.domain.model.patient.Patient;
import se.inera.fmu.domain.shared.AbstractAuditingEntity;
import se.inera.fmu.domain.shared.IEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Created by Rasheed on 7/7/14.
 */
@Entity
@Table(name = "T_EAVROP")
@ToString
public class Eavrop extends AbstractAuditingEntity implements IEntity<Eavrop> {

    //~ Instance fields ================================================================================================

    // database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eavrop_id", updatable = false, nullable = false)
    private Long eavropId;

    // business key!
    @NotNull
    @Embedded
    private ArendeId arendeId;

    @Column(name = "utredning_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UtredningType utredningType;

    @Column(name = "tolk", length = 50)
    @Max(50)
    private String tolk;

    @ManyToOne
    private Patient patient;

    //~ Constructors ===================================================================================================

    Eavrop() {
        //Needed by hibernate
    }

    /**
     *
     * @param arendeId
     */
    public Eavrop(final ArendeId arendeId, final UtredningType utredningType, final String tolk, final Patient patient) {
        Validate.notNull(arendeId);
        setArendeId(arendeId);
        setUtredningType(utredningType);
        setTolk(tolk);
        setPatient(patient);
    }

    //~ Property Methods ===============================================================================================

    /**
     * The arendeId is the identity of this entity, and is unique.
     *
     * @return arendeId
     */
    public ArendeId getArendeId() {
        return arendeId;
    }

    public UtredningType getUtredningType() {
        return utredningType;
    }

    public String getTolk() {
        return tolk;
    }

    public Patient getPatient() {
        return patient;
    }

    private void setTolk(final String tolk) {
        this.tolk = tolk;
    }

    private void setUtredningType(final UtredningType utredningType) {
        this.utredningType = utredningType;
    }

    private void setArendeId(ArendeId arendeId) {
        this.arendeId = arendeId;
    }

    private void setPatient(Patient patient) {
        this.patient = patient;
    }

    public EavropId getEavropId() {
        return new EavropId(this.eavropId);
    }

    //~ Other Methods ==================================================================================================

    @Override
    public boolean sameIdentityAs(final Eavrop other) {
        return other != null && this.getArendeId().sameValueAs(other.getArendeId());
    }

    /**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Eavrop)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final Eavrop other = (Eavrop) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of tracking id.
     */
    @Override
    public int hashCode() {
        return arendeId.hashCode();
    }
}
