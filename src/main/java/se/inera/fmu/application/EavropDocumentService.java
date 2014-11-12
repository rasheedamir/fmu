package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropDocumentService {
	
	//public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop, DateTime documentsSentFromBestallareDateTime);
	
	public void addReceivedExternalDocument(AddReceivedExternalDocumentCommand aCommand);

	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand);
	
	public void addRequestedDocument(AddRequestedDocumentCommand aCommand);
	
}
