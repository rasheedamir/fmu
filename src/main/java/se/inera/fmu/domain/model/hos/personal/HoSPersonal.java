package se.inera.fmu.domain.model.hos.personal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.model.hos.hsa.HsaBefattning;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISC", discriminatorType=DiscriminatorType.STRING, length=4)
@DiscriminatorValue("HOS")
@Table(name = "T_HOSPERSONAL",  uniqueConstraints=@UniqueConstraint(columnNames="HSA_ID"))
@ToString
public class HoSPersonal extends AbstractBaseEntity implements IEntity<HoSPersonal> {

	private static final long serialVersionUID = 1L;
	
	// database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HOSPERSONAL_ID", updatable = false, nullable = false)
    private Long Id;

	// business id
    @NotNull
    @Embedded
    private HsaId hsaId;
    
    @NotNull
    @Embedded
    private Name name;

    @Embedded
    private HsaBefattning hsaBefattning;
        
    @ManyToOne
    @JoinColumn(name = "VARDGIVARENHET_ID")
    private Vardgivarenhet vardgivarenhet;
    
    //~ Constructors ===================================================================================================

    public HoSPersonal() {
        //Needed by hibernate
    }
    
    /**
     *
     */
    public HoSPersonal(final HsaId hsaId, final Name name ) {

        Validate.notNull(hsaId);
        this.setHsaId(hsaId);
        Validate.notNull(name);
        this.setName(name);

    }

    //~ Property Methods ===============================================================================================

    public HsaId getHsaId() {
		return hsaId;
	}

	private void setHsaId(HsaId hsaId) {
		this.hsaId = hsaId;
	}

    public Name getName() {
		return name;
	}

	private void setName(Name name) {
		
		this.name = name;
	}

	public HsaBefattning getHsaBefattning() {
		return this.hsaBefattning;
	}

	public void setHsaBefattning(HsaBefattning hsaBefattning) {
		this.hsaBefattning = hsaBefattning;
	}

	public Vardgivarenhet getVardgivarenhet() {
		return this.vardgivarenhet;
	}

	public void setVardgivarenhet(Vardgivarenhet vardgivarenhet) {
		this.vardgivarenhet = vardgivarenhet;
	}
	
	//~ Other Methods ==================================================================================================

	@Override
    public boolean sameIdentityAs(final HoSPersonal other) {
        return other != null && this.getHsaId().equals(other.getHsaId());
    }

    /**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Vardgivare)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        final HoSPersonal other = (HoSPersonal) object;
        return sameIdentityAs(other);
    }

    /**
     * @return Hash code of tracking id.
     */
    @Override
    public int hashCode() {
        return getHsaId().hashCode();
    }

}
