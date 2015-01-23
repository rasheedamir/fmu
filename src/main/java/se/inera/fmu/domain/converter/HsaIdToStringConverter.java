package se.inera.fmu.domain.converter;

import javax.persistence.AttributeConverter;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

public class HsaIdToStringConverter implements AttributeConverter<HsaId, String> {

	@Override
    public String convertToDatabaseColumn(HsaId value) {
        return (value != null) ? value.getHsaId() : null;            
    }    

    @Override
    public HsaId convertToEntityAttribute(String value) {
    	return (value != null) ? new HsaId(value) : null;
    }

}
