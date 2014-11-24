package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropIntygService {
	
	public void addIntygSentInformation(AddIntygSentCommand intygCommand);

	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand intygCommand);
	
	public void addIntygApprovedInformation(AddIntygApprovedCommand intygCommand);

	
}
