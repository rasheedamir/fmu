package se.inera.fmu.domain.model.eavrop;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

/**
 * Domain event describing when the eavrop has been approved by the customer/beställare
 *
 */
public class EavropApprovedByBestallareEvent extends EavropEvent{
	
	private final DateTime approvalDateTime; 
	
	//~ Constructors ===================================================================================================
    
	public EavropApprovedByBestallareEvent(final EavropId eavropId, final DateTime approvalDateTime) {
		super(eavropId);
		Validate.notNull(approvalDateTime);
		this.approvalDateTime= approvalDateTime;
	}

	//~ Property Methods ===============================================================================================

	public DateTime getApprovalDateTime() {
		return this.approvalDateTime;
	}

	//~ Other Methods ==================================================================================================
}
