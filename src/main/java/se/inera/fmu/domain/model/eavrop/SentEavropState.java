package se.inera.fmu.domain.model.eavrop;

import lombok.extern.slf4j.Slf4j;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentInformation;

/**
 * When an Eavrop is sent we await the approval, then the Eavrop statewise will be Approved
 */
@Slf4j
public class SentEavropState extends AbstractNoteableEavropState{

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
