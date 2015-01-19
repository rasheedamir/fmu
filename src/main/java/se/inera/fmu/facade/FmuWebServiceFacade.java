package se.inera.fmu.facade;

import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;

/**
 * Interface implemented by the facade.
 *
 * @author Rickard
 */
public interface FmuWebServiceFacade {
	
	/****************************************************************
	 * Eavrop creation
	 ****************************************************************/
	public ArendeId createEavrop(CreateEavropCommand aCommand);
	
	
	/****************************************************************
	 * Booking responses
	 ****************************************************************/
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand responseCommand);
	
	/****************************************************************
	 * Documents
	 ****************************************************************/
	public void addReceivedExternalDocument(AddReceivedExternalDocumentsCommand aCommand);
	
	/****************************************************************
	 * Intyg informations
	 ****************************************************************/
	public void addIntygSentInformation(AddIntygSentCommand aCommand);

	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand aCommand);
	
	public void addIntygApprovedInformation(AddIntygApprovedCommand aCommand);
	
	/****************************************************************
	 * Eavrop approvals
	 ****************************************************************/
	public void approveEavrop(ApproveEavropCommand aCommand);
	
	public void approveEavropCompensation(ApproveEavropCompensationCommand aCommand);


}
