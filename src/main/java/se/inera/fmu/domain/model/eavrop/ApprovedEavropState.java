package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;

/**
 * When an Eavrop is approved we only await the compensation approval, then the Eavrop statewise will be closed
 */
@Slf4j
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
		log.info("State transition APPROVED -> CLOSED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
}
