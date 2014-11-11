package se.inera.fmu.application;

import se.inera.fmu.application.impl.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.AddIntygSignedCommand;
import se.inera.fmu.application.impl.AddNoteCommand;
import se.inera.fmu.application.impl.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.RemoveNoteCommand;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.intyg.IntygApprovedInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygComplementRequestInformation;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSignedInformation;
import se.inera.fmu.domain.model.eavrop.note.Note;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropIntygDomainService {
	
	public void addIntygSignedInformation(AddIntygSignedCommand intygCommand);

	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand intygCommand);
	
	public void addIntygApprovedInformation(AddIntygApprovedCommand intygCommand);

	
}
