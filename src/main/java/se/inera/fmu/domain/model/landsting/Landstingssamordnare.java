package se.inera.fmu.domain.model.landsting;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Email;

import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.hos.hsa.HsaBefattning;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.personal.HoSPersonal;
import se.inera.fmu.domain.model.shared.Name;

@Entity
@DiscriminatorValue("LSAM")
public class Landstingssamordnare extends HoSPersonal {

	@ManyToOne
	@JoinColumn(name = "LANDSTING_ID")
	private Landsting landsting; //TODO:should this mapping be in superclass
	
    @Email
    @Column(name = "EMAIL")
    private String email;


	Landstingssamordnare() {
		// Needed by hibernate
	}

	public Landstingssamordnare(final HsaId hsaId, final Name name, final HsaBefattning hsaBefattning, final Landsting landsting, final String email) {
		super(hsaId, name);
		Validate.notNull(landsting);
		this.setLandsting(landsting);
		this.setHsaBefattning(hsaBefattning);
		if(!StringUtils.isBlankOrNull(email)){
			if(isValidEmailAddress(email)){
				this.setEmail(email);	
			}else{
				throw new IllegalArgumentException(String.format("Not a valid email address %s ", email));
			}
		}
	}
	
	public static boolean isValidEmailAddress(String emailStr) {
		return EmailValidator.getInstance().isValid(emailStr);
	}

	private void setLandsting(Landsting landsting){
		this.landsting = landsting;
	}
	
	public Landsting getLandsting(){
		return this.landsting;
	}
	
	private void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}

}
