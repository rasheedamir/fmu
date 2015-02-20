package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;
/**
 * The Sent state tells that the intyg related to the Eavrop have been created, signed and sent to the customer/beställare. 
 * Available behavior is addIntygSent, addIntygComplementRequest, addIntygApproved and approveEavrop but only the latter will 
 * result in a state transition.
 * AddIntygSent, no state transition
 * AddIntygComplementRequest, no state transition
 * AddIntygApproved, no state transition
 * ApproveEavrop will move Eavrop forward into Approved state.
 */
@Slf4j
public class SentEavropState extends AbstractNoteableEavropState{

	private static final long serialVersionUID = 1L;

	@Override
	public EavropStateType getEavropStateType() {
		return EavropStateType.SENT;
	}

	@Override
	public void approveEavrop(Eavrop eavrop, EavropApproval eavropApproval){
		eavrop.setEavropApproval(eavropApproval);
		
		//State transition SENT -> APPROVED
		eavrop.setEavropState(new ApprovedEavropState());
		log.info("State transition SENT -> APPROVED for ARENDE ID {} EAVROP ID {} ", eavrop.getArendeId().toString(), eavrop.getEavropId().toString());
	}
	
	@Override
	public void addIntygSentInformation(Eavrop eavrop, IntygSentInformation intygSentInformation){
		eavrop.addToIntygSentInformation(intygSentInformation);
		//No state transition
	}
	
	@Override
	public void addIntygComplementRequestInformation(Eavrop eavrop, IntygComplementRequestInformation intygComplementRequestInformation){
		eavrop.addToIntygComplementRequestInformation(intygComplementRequestInformation);
		//No state transition
	}
	
	@Override
	public void addIntygApprovedInformation(Eavrop eavrop, IntygApprovedInformation intygApprovedInformation){
		eavrop.addToIntygApprovedInformation(intygApprovedInformation);
		//No state transition
	}
}
