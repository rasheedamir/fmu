package se.inera.fmu.domain.model.hos.vardgivare;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_VARDGIVARENHET" , uniqueConstraints=@UniqueConstraint(columnNames="HSA_ID"))
public class Vardgivarenhet extends AbstractBaseEntity implements IEntity<Vardgivarenhet> {

    //~ Instance fields ================================================================================================
	
	private static final long serialVersionUID = 1L;

	// database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VARDGIVARENHET_ID", updatable = false, nullable = false)
    private Long Id;

	// business id
    @NotNull
    @Embedded
    private HsaId hsaId;
    
    @NotNull
    @Size(min = 0, max = 255)
    @Column(name = "UNIT_NAME", nullable = false)
    private String unitName;

    //TODO: arbetsplatskod?
    
    @Embedded
    private Address address;    
    
    @ManyToOne
    @JoinColumn(name = "VARDGIVARE_ID")
    private Vardgivare vardgivare;

    
    @ManyToMany(mappedBy="vardgivarenheter")
    private Set<Landsting> landsting;

    
    //~ Constructors ===================================================================================================

    Vardgivarenhet() {
        //Needed by hibernate
    }

    public Vardgivarenhet(final Vardgivare vardgivare, final HsaId hsaId, final String unitName, final Address address) {
    	Validate.notNull(vardgivare);
    	this.setVardgivare(vardgivare);
    	Validate.notNull(hsaId);
        this.setHsaId(hsaId);
        Validate.notBlank(unitName);
        this.setUnitName(unitName);
        this.setAddress(address);
    }

    //~ Property Methods ===============================================================================================

    public HsaId getHsaId(){
    	return this.hsaId; 
    }
    
    public Long getId() {
		return Id;
	}

	private void setHsaId(HsaId hsaId){
    	this.hsaId = hsaId; 
    }

    public String getUnitName() {
		return this.unitName;
	}

	private void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Address getAddress() {
		return address;
	}

	private void setAddress(Address address) {
		this.address = address;
	}

	public Vardgivare getVardgivare() {
		return vardgivare;
	}

	private void setVardgivare(Vardgivare vardgivare) {
		this.vardgivare = vardgivare;
	}

	public Set<Landsting> getLandsting() {
		return landsting;
	}

	private void setLandsting(Set<Landsting> landsting) {
		this.landsting = landsting;
	}

	public void addLandsting(Landsting landsting) {
		if(this.landsting == null){
			this.landsting = new HashSet<Landsting>();
		}
		this.landsting.add(landsting);
	}
	
	//~ Other Methods ==================================================================================================

	@Override
    public boolean sameIdentityAs(final Vardgivarenhet other) {
        return other != null && this.getHsaId().equals(other.getHsaId());
    }

    /**
     * @param object to compare
     * @return True if they have the same identity
     * @see #sameIdentityAs(Vardgivarenhet)
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object){
        	return true;
        }
        if (object == null || getClass() != object.getClass()){
        	return false;
        }

        final Vardgivarenhet other = (Vardgivarenhet) object;
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
