package se.inera.fmu.domain.model.systemparameter;

import static se.inera.fmu.application.util.StringUtils.isBlankOrNull;
import static se.inera.fmu.application.util.StringUtils.safeTrimToNullIfBlank;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Component;


/**
 * Component in the FMU application that handles system configuration.
 *
 * @author Rickard
 */
@Component
public class Configuration {

	
	@Inject 
	SystemParameterRepository systemParameterRepository;
	
	// Eavrop calcualtion property keys
	public static final String KEY_EAVROP_START_DATE_OFFSET = "fmu.domain.model.eavrop.startDateOffset";
	public static final String KEY_EAVROP_ACCEPTANCE_VALID_LENGTH = "fmu.domain.model.eavrop.acceptanceValidLength";
	public static final String KEY_EAVROP_ASSESSMENT_VALID_LENGTH = "fmu.domain.model.eavrop.assessmentValidLength";
	public static final String KEY_EAVROP_COMPLETION_VALID_LENGTH = "fmu.domain.model.eavrop.completionValidLength";

	
	
	private String getSystemParameterValue(String key){
		SystemParameter sp = systemParameterRepository.findByKey(key);
		if(sp != null){
			return sp.getValue();
		}
		
		return null;
	}
	
	private SystemParameter getSystemParameter(String key){
		return systemParameterRepository.findByKey(key);
	}

	public String getString(String key) {
		return getString(key, null);
	}

	public String getString(String key, String defaultValue) {
		String value = getSystemParameterValue(key);
		if (isBlankOrNull(value) && getSystemParameter(key)==null) {
			value = defaultValue;
		}
		return value;
	}

	
	public Long getLong(String key) {
		if (getSystemParameter(key) != null) {
			return getLong(key, Long.valueOf(0L));
		}
		return null;
	}

	public Long getLong(String key, Long defaultValue) {
		String value = safeTrimToNullIfBlank(getString(key));
		if (value != null) {
			return Long.valueOf(value);
		}
		return defaultValue;
	}

	public Integer getInteger(String key) {
		if (getSystemParameter(key) != null) {
			return getInteger(key, Integer.valueOf(0));
		}
		return null;
	}

	
	public Integer getInteger(String key, Integer defaultValue) {
		String value = safeTrimToNullIfBlank(getString(key));
		if (value != null) {
			return Integer.valueOf(value);
		}
		return defaultValue;
	}

	public Double getDouble(String key) {
		if (getSystemParameter(key) != null) {
			return getDouble(key, Double.valueOf(0));
		}
		return null;
	}
		
	public Double getDouble(String key, Double defaultValue) {
		String value = safeTrimToNullIfBlank(getString(key));
		if (value != null) {
			return Double.valueOf(value);
		}
		return defaultValue;
	}

	public File getFile(String key) {
		return getFile(key, null);
	}

	public File getFile(String key, File defaultValue) {
		String value = safeTrimToNullIfBlank(getString(key));
		if (value != null) {
			return new File(value);
		}
		return defaultValue;
	}

	public boolean getBoolean(String key) {
		return getSystemParameter(key) != null && getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = safeTrimToNullIfBlank(getString(key));
		if (value != null) {
			return Boolean.valueOf(value);
		}
		return defaultValue;
	}

}

