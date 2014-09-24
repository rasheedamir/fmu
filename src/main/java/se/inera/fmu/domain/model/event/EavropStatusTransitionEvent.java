package se.inera.fmu.domain.model.event;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

import se.inera.fmu.domain.model.eavrop.EavropStatus;

public class EavropStatusTransitionEvent extends EavropEvent {

    //~ Instance fields ================================================================================================

    @Column(name = "OLD_STATUS", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EavropStatus oldStatus;

    @Column(name = "NEW_STATUS", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private EavropStatus newStatus;
    

    //~ Constructors ===================================================================================================

    EavropStatusTransitionEvent() {
        //Needed by hibernate
    }

    
    public EavropStatusTransitionEvent(final LocalDateTime eavropEventDateTime, final EavropStatus fromStatus, final EavropStatus toStatus ) {
    	super(eavropEventDateTime);
    	Validate.notNull(fromStatus);
    	Validate.notNull(toStatus);
    	setOldStatus(oldStatus);
    	setNewStatus(newStatus);
        
   }


    //~ Property Methods ===============================================================================================

    public EavropStatus getOldStatus() {
		return this.oldStatus;
		
	}

    private void setOldStatus(EavropStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

    public EavropStatus getNewStatus() {
		return this.newStatus;
		
	}

    private void setNewStatus(EavropStatus newStatus) {
		this.newStatus = newStatus;
	}
	
}
