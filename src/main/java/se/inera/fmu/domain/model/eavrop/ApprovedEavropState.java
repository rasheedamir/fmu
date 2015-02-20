package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;

/**
 * The Approved state is achieved when the customer communicates that the eavrop have been approved 
 * 
 * Available behavior besides Notes is compensation approval.
 * CompensationApproval will move Eavrop forward into Closed state.
 */
@Slf4j
public class ApprovedEavropState extends AbstractNoteableEavropState{

	private static final long serialVersionUID = 1L;

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.APPROVED;
	}

	@Override
	public void approveEavropCompensation(Eavrop eavrop, EavropCompensationApproval eavropCompensationApproval){
		eavrop.setEavropCompensationApproval(eavropCompensationApproval);
		
		//State transition APPROVED -> CLOSED
		eavrop.setEavropState(new ClosedEavropState());
		log.info("State transition APPROVED -> CLOSED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
}
