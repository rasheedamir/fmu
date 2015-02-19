package se.inera.fmu.domain.model.eavrop;

/**
 * Domain event describing when the eavrop has been closed by the customer/beställare
 * The eavrop gets closed when the customer states that the compensation for the eavrop has been approved
 *
 */
public class EavropClosedByBestallareEvent extends EavropEvent{
	
	//~ Constructors ===================================================================================================
	public EavropClosedByBestallareEvent(final EavropId eavropId) {
		super(eavropId);
	}

	//~ Property Methods ===============================================================================================
}
