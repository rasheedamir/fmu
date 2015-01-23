package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.domain.model.eavrop.EavropId;

/**
 * Service for handling information about intyg connected to the Eavrop 
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropIntygService {
	
	/**
	 * Adds information to the eavrop that a Intyg has been signed and sent to the bestallare
	 * @param intygCommand
	 * @return 
	 */
	public EavropId addIntygSentInformation(AddIntygSentCommand intygCommand);

	/**
	 * Adds information to the eavrop that the bestallare has requested a new Intyg with complements
	 * @param intygCommand
	 */
	public EavropId addIntygComplementRequestInformation(AddIntygComplementRequestCommand intygCommand);
	
	
	/**
	 * Adds information to the eavrop that the bestallare has approved an Intyg ordered by the bestallare
	 * @param intygCommand
	 */
	public EavropId addIntygApprovedInformation(AddIntygApprovedCommand intygCommand);

	
}
