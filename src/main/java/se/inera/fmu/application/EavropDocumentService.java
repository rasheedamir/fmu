package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropDocumentService {

	public void addReceivedExternalDocument(AddReceivedExternalDocumentsCommand aCommand);

	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand);

	public void addRequestedDocument(AddRequestedDocumentCommand aCommand);

}
