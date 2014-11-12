package se.inera.fmu.domain.model.landsting;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.ToString;

import org.apache.commons.lang.Validate;

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
	    @Column(name = "LANDSTING_ID", updatable = false, nullable = false)
	    private Long id;

		// business id
	    @NotNull
	    @Embedded
	    private LandstingCode landstingCode;

	    @Column(name = "NAME", nullable = false)
	    private String name;
	    
	    @ManyToMany
	    @JoinTable(
	    	      name="R_LANDSTING_VARDGIVARENHET",
	    	      joinColumns={@JoinColumn(name="LANDSTING_ID", referencedColumnName="LANDSTING_ID")},
	    	      inverseJoinColumns={@JoinColumn(name="VARDGIVARENHET_ID", referencedColumnName="VARDGIVARENHET_ID")})
	    private Set<Vardgivarenhet> vardgivarenheter;
	    
	    @ManyToMany
	    @JoinTable(name="R_LANDSTING_HOSPERSONAL",
	      joinColumns={ @JoinColumn (name="LANDSTING_ID", referencedColumnName="LANDSTING_ID")},
	      inverseJoinColumns={@JoinColumn(name="HOSPERSONAL_ID", referencedColumnName="HOSPERSONAL_ID")})
	    private Set<Landstingssamordnare> landstingssamordnare;
	    
	    //~ Constructors ===================================================================================================

	    Landsting() {
	        //Needed by hibernate
	    }

	    public Landsting(final LandstingCode landstingCode,  final String name){
	    	Validate.notNull(landstingCode);
	    	Validate.notEmpty(name);
	    	this.setLandstingCode(landstingCode);
	    	this.setName(name);
	    }

	    //~ Property Methods ===============================================================================================

	    public LandstingCode getLandstingCode() {
			return this.landstingCode;
		}

		private void setLandstingCode(LandstingCode landstingCode) {
			this.landstingCode = landstingCode;
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
	        return other != null && this.getLandstingCode().equals(other.getLandstingCode());
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
	        return getLandstingCode().hashCode();
	    }
}
