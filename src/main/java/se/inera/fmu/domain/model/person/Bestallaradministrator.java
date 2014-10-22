package se.inera.fmu.domain.model.person;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.Email;

import lombok.ToString;

@Entity
@ToString
public class Bestallaradministrator extends Person {
    
//	//TODO:Same as bestallare i.e organisation or unit? move to Person!
//	@Column(name = "LFC", nullable = false)
//    private String lfc;

	@Column(name = "PHONE", nullable = true)
    private String phone;
       
    @Email
    @Column(name = "EMAIL", nullable = true)
    private String email;
    
    //~ Constructors ===================================================================================================

    Bestallaradministrator() {
        //Needed by hibernate
    }

    public Bestallaradministrator(final String name, String befattning, String organisation, String phone, String email){
    	super(name,  befattning, organisation);
    	Validate.notEmpty(organisation);
    	setPhone(phone);
    	setEmail(email);
    }

    //~ Property Methods ===============================================================================================

	public String getPhone() {
		return this.phone;
	}

	private void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

}
