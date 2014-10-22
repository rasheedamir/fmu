package se.inera.fmu.domain.model.eavrop;

/**
 * When an Eavrop is approved we only await the compensation approval
 */
public class ApprovedEavropState extends AbstractNoteableEavropState{

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.APPROVED;
	}

	@Override
	public void approveEavropCompensation(Eavrop eavrop, EavropCompensationApproval eavropCompensationApproval){
		eavrop.setEavropCompensationApproval(eavropCompensationApproval);
				
		eavrop.handleEavropCompensationApproval();
		
		eavrop.setEavropState(new ClosedEavropState());
	}
}
