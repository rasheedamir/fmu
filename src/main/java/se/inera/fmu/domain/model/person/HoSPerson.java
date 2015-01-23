package se.inera.fmu.domain.model.person;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;
import se.inera.fmu.domain.converter.HsaIdToStringConverter;
import se.inera.fmu.domain.model.hos.hsa.HsaId;

@Entity
@ToString
@DiscriminatorValue("HOS")
public class HoSPerson extends Person {
	
//	@Embedded
//	@AttributeOverride(name="hsaId", column=@Column(name = "HSA_ID", nullable = true, updatable = false, unique = false))
//	private HsaId hsaId;
	
	
	//@AttributeOverride does seem to be ignored by Liquibase doing a conversion instead
	@Column(name = "HSA_ID", nullable = true, updatable = false, unique = false, columnDefinition="varchar2(64)")
	private String hsaIdString;
	
	private final static HsaIdToStringConverter converter = new HsaIdToStringConverter();
	
	HoSPerson() {
        //Needed by hibernate
    }
	public HoSPerson( final HsaId hsaId, final String name, String role, String organisation, String unit){
		this(hsaId, name, role, organisation, unit, null, null);
	}
	
    public HoSPerson(final HsaId hsaId, final String name, String role, String organisation, String unit, String phone, String email){
    	super(name, role, organisation, unit, phone, email);
    	this.setHsaId(hsaId);
    }
    
    public HsaId getHsaId(){
    	return new HsaIdToStringConverter().convertToEntityAttribute(this.hsaIdString);    
    	}
    
    private void setHsaId(HsaId hsaId){
    	this.hsaIdString = converter.convertToDatabaseColumn(hsaId);
    }
    
    

}
