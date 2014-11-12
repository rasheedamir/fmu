package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSignedCommand;
import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropIntygService {
	
	public void addIntygSignedInformation(AddIntygSignedCommand intygCommand);

	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand intygCommand);
	
	public void addIntygApprovedInformation(AddIntygApprovedCommand intygCommand);

	
}
