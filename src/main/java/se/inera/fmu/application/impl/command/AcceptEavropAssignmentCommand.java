package se.inera.fmu.application.impl.command;

import org.apache.commons.lang.Validate;

import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AcceptEavropAssignmentCommand {
	private EavropId eavropId;
	private HsaId hsaId;
	
	public AcceptEavropAssignmentCommand(EavropId eavropId, HsaId hsaId) {
		super();
		this.eavropId = eavropId;
		this.hsaId = hsaId;
		validate();
	}
	
	private void validate(){
		Validate.notNull(this.eavropId);
		Validate.notNull(this.hsaId);
	}

}
