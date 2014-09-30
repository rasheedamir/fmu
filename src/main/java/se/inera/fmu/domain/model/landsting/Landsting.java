package se.inera.fmu.domain.model.landsting;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

import lombok.ToString;


import se.inera.fmu.domain.model.hos.vardgivare.Vardgivare;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.shared.AbstractBaseEntity;
import se.inera.fmu.domain.shared.IEntity;

@Entity
@Table(name = "T_LANDSTING",  uniqueConstraints=@UniqueConstraint(columnNames="LANDSTING_ID"))
@ToString
public class Landsting extends AbstractBaseEntity implements IEntity<Landsting>{
		
	    //~ Instance fields ================================================================================================

	   private static final long serialVersionUID = 1L;

		// database primary key
	   //TODO: Is this neccessary use business id as primary key 
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "ID", updatable = false, nullable = false)
	    private Long id;

		// business id
	    @NotNull
	    @Embedded
	    private LandstingId landstingId;

	    @Column(name = "NAME", nullable = false)
	    private String name;
	    
	    //TODO: Is this relation necessary? 
//	    @ManyToMany
//	    private Set<Vardgivare> vardgivare;
	    
	    @ManyToMany
	    private Set<Vardgivarenhet> vardgivarenheter;
	    
	    @ManyToMany
	    private Set<Landstingssamordnare> landstingssamordnare;
	    
	    //~ Constructors ===================================================================================================

	    Landsting() {
	        //Needed by hibernate
	    }

	    public Landsting(final LandstingId landstingId,  final String name){
	    	Validate.notNull(landstingId);
	    	Validate.notEmpty(name);
	    	this.setLandstingId(landstingId);
	    	this.setName(name);
	    }

	    //~ Property Methods ===============================================================================================

	    public LandstingId getLandstingId() {
			return this.landstingId;
		}

		private void setLandstingId(LandstingId landstingId) {
			this.landstingId = landstingId;
		}

	    public String getName() {
			return name;
		}

		private void setName(String name) {
			this.name = name;
		}
	    
	    public Set<Vardgivarenhet> getVardgivarenheter() {
			return vardgivarenheter;
		}

		private void setVardgivarenheter(Set<Vardgivarenhet> vardgivarenheter) {
			this.vardgivarenheter = vardgivarenheter;
		}

	    public void addVardgivarenhet(Vardgivarenhet vardgivarenhet){
	    	if(this.vardgivarenheter == null){
	    		this.vardgivarenheter = new HashSet<Vardgivarenhet>();
	    	}
	    	this.vardgivarenheter.add(vardgivarenhet);
	    }
		
	    public Set<Landstingssamordnare> getLandstingssamordnare() {
			return landstingssamordnare;
		}

		private void setLandstingssamordnare(Set<Landstingssamordnare> samordnare) {
			this.landstingssamordnare = samordnare;
		}

	    public boolean addLandstingssamordnare(Landstingssamordnare landstingssamordnare){
	    	if(this.landstingssamordnare == null){
	    		this.landstingssamordnare = new HashSet<Landstingssamordnare>();
	    	}
	    	return this.landstingssamordnare.add(landstingssamordnare);
	    }
		
		//~ Other Methods ==================================================================================================

		@Override
	    public boolean sameIdentityAs(final Landsting other) {
	        return other != null && this.getLandstingId().equals(other.getLandstingId());
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

	        final Landsting other = (Landsting) object;
	        return sameIdentityAs(other);
	    }

	    /**
	     * @return Hash code of tracking id.
	     */
	    @Override
	    public int hashCode() {
	        return getLandstingId().hashCode();
	    }
}
