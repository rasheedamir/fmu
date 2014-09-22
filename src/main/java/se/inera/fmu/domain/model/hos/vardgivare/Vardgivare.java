package se.inera.fmu.domain.model.hos.vardgivare;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang3.Validate;

import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

/**
 * Created by Rickard on 8/26/14.
 * 
 * This class corresponds to the 'Vårdgivare' entity in the Domain Information Model.  	
 */


@Entity
@Table(name = "T_VARDGIVARE", uniqueConstraints=@UniqueConstraint(columnNames="HSA_ID"))
@ToString
public class Vardgivare extends AbstractBaseEntity implements IEntity<Vardgivare> {
	
    //~ Instance fields ================================================================================================

   private static final long serialVersionUID = 1L;

	// database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

	// business id
    @NotNull
    @Embedded
    private HsaId hsaId;

    @Column(name = "NAME", nullable = false)
    private String name;

    //TODO: Probably not necessary relation, go through vargivareenheter instead
//    @ManyToMany(mappedBy = "vardgivare")
//    private Set<Landsting> landsting;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vardgivare")
    private Set<Vardgivarenhet> vardgivarenheter;
    
    
    //~ Constructors ===================================================================================================

	Vardgivare() {
        //Needed by hibernate
    }

    public Vardgivare(final HsaId hsaId,  final String name){
    	Validate.notNull(hsaId);
    	Validate.notBlank(name);
    	this.setHsaId(hsaId);
    	this.setName(name);
    }
	
    //~ Property Methods ===============================================================================================

    public HsaId getHsaId() {
		return hsaId;
	}

	private void setHsaId(HsaId hsaId) {
		this.hsaId = hsaId;
	}

    public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	//See comment  on property variable
//    public Set<Landsting> getLandsting() {
//		return this.landsting;
//	}
//
//	private void setLandsting(Set<Landsting> landsting) {
//		this.landsting = landsting;
//	}
//
//    public boolean addLandsting(Landsting landsting){
//    	if(this.landsting == null){
//    		this.landsting = new HashSet<Landsting>();
//    	}
//    	return this.landsting.add(landsting);
//    }

    public Set<Vardgivarenhet> getVardgivarenheter() {
		return this.vardgivarenheter;
	}

	private void setVardgivarenheter(Set<Vardgivarenhet> vardgivarenheter) {
		this.vardgivarenheter = vardgivarenheter;
	}

    public boolean addVardgivarenhet(Vardgivarenhet vardgivarenhet){
    	if(this.vardgivarenheter == null){
    		this.vardgivarenheter = new HashSet<Vardgivarenhet>();
    	}
    	return this.vardgivarenheter.add(vardgivarenhet);
    }

	//~ Other Methods ==================================================================================================

	@Override
    public boolean sameIdentityAs(final Vardgivare other) {
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

        final Vardgivare other = (Vardgivare) object;
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
