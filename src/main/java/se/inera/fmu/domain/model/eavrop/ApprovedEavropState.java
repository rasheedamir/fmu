package se.inera.fmu.domain.model.eavrop;

/**
 * When an Eavrop is approved we only await the compensation approval, then the Eavrop statewise will be closed
 */
public class ApprovedEavropState extends AbstractNoteableEavropState{

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.APPROVED;
	}

	@Override
	public void approveEavropCompensation(Eavrop eavrop, EavropCompensationApproval eavropCompensationApproval){
		eavrop.setEavropCompensationApproval(eavropCompensationApproval);
		
		//State transition APPROVED -> CLOSED
		eavrop.setEavropState(new ClosedEavropState());
	}
}
