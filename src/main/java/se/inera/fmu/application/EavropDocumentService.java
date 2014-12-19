package se.inera.fmu.application;

import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;

/**
 * Service for handling eavrop documents and events related to those
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface EavropDocumentService {
	

	/**
	 * Adds to the eavrop an externally received document, will potentially affect the start date of the eavrop assessment period 
	 *
	 * @param aCommand
	 */
	public void addReceivedExternalDocument(AddReceivedExternalDocumentsCommand aCommand);

	/**
	 * Adds to the eavrop an internally received document 
	 *
	 * @param aCommand
	 */
	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand);

	/**
	 * Adds to the eavrop a requested document
	 * @param aCommand
	 */
	public void addRequestedDocument(AddRequestedDocumentCommand aCommand);

}
