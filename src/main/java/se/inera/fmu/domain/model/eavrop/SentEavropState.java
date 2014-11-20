package se.inera.fmu.domain.model.eavrop;

import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;

/**
 * When an Eavrop is sent we await the approval, then the Eavrop statewise will be Approved
 */
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
	}
	
	@Override
	public void addIntygSignedInformation(Eavrop eavrop, IntygSignedInformation intygSignedInformation){
		eavrop.addToIntygSignedInformation(intygSignedInformation);
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
