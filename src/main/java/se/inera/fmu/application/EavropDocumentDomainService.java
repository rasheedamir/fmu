package se.inera.fmu.application;

import se.inera.fmu.application.impl.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.AddRequestedDocumentCommand;

/**
 * Created by Rickard on 01/11/14.
 */
public interface EavropDocumentDomainService {
	
	//public void setDocumentsSentFromBestallareDateTime(Eavrop eavrop, DateTime documentsSentFromBestallareDateTime);
	
	public void addReceivedExternalDocument(AddReceivedExternalDocumentCommand aCommand);

	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand);
	
	public void addRequestedDocument(AddRequestedDocumentCommand aCommand);
	
}
