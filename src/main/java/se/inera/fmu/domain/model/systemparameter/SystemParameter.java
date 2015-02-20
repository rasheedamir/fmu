package se.inera.fmu.domain.model.systemparameter;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import se.inera.fmu.application.util.ObjectUtils;

/**
 * A System parameter entity  
 *
 */
@Entity
@Table(name = "T_SYSTEM_PARAMETER")
@Cacheable(false) 
public class SystemParameter implements Serializable {

	private static final long serialVersionUID = 2014100601L;

	@Id
	@Column(name = "ID")
	private Long systemParameterId;

	@Column(name = "KEY")
	private String key;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "DESCRIPTION")
	private String description;

	public Long getId() {
		return systemParameterId;
	}

	public void setId(Long id) {
		this.systemParameterId = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.safeHashCode(systemParameterId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SystemParameter)) {
			return false;
		}
		SystemParameter other = (SystemParameter) obj;
		if (getId() != null && other.getId() != null) {
			return getId().equals(other.getId());
		}
		return (key.equals(other.getKey()));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemProperty [id: ").append(getId()).append(", key ,").append(getKey()).append(" value ").append(getValue()).append("]");
		return builder.toString();
	}
	
}
