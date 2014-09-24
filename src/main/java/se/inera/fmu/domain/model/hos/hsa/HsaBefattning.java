package se.inera.fmu.domain.model.hos.hsa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.Validate;



@Embeddable
public class HsaBefattning {

    //~ Instance fields ================================================================================================

    @Column(name = "BEFATTNINGSKOD")
    private String befattningskod ;

    @Column(name = "BEFATTNINGSBESKRIVNING")
    private String beskrivning;

    //TODO: Implement class according to HSA befattning
    
    //~ Constructors ===================================================================================================

    HsaBefattning() {
        // Needed by Hibernate
    }

    /**
     *
     */
    public HsaBefattning(final String befattningskod, final String beskrivning) {
        Validate.notBlank(befattningskod);
    	Validate.notBlank(beskrivning);
    	this.setBefattningskod(befattningskod);
    	this.setBeskrivning(beskrivning);
    }

	public String getBefattningskod() {
		return befattningskod;
	}

	private void setBefattningskod(String befattningskod) {
		this.befattningskod = befattningskod;
	}

	public String getBeskrivning() {
		return beskrivning;
	}

	private void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}

    //~ Property Methods ===============================================================================================

    
}
