package se.inera.fmu.domain.model.eavrop;

/**
 * Closed state is the end state of the Eavrop
 *	
 * There are no available behavior when the state is closed.
 */
public class ClosedEavropState extends AbstractEavropState {

	private static final long serialVersionUID = 1L;

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.CLOSED;
	}
}
