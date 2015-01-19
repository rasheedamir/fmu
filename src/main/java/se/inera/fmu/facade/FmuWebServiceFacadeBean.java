package se.inera.fmu.facade;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import se.inera.fmu.application.EavropApprovalService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.EavropDocumentService;
import se.inera.fmu.application.EavropIntygService;
import se.inera.fmu.application.FmuEventService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.AddBookingDeviationResponseCommand;
import se.inera.fmu.application.impl.command.AddIntygApprovedCommand;
import se.inera.fmu.application.impl.command.AddIntygComplementRequestCommand;
import se.inera.fmu.application.impl.command.AddIntygSentCommand;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCommand;
import se.inera.fmu.application.impl.command.ApproveEavropCompensationCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingDeviationResponseType;

@Slf4j
@Service
public class FmuWebServiceFacadeBean  implements FmuWebServiceFacade {

    @Inject
    private FmuOrderingService fmuOrderingService;
    @Inject
    private EavropDocumentService eavropDocumentService;
    @Inject
    private EavropBookingService eavropBookingService;
    @Inject
    private EavropIntygService eavropIntygService;
    @Inject
    private EavropApprovalService eavropApprovalService;
    @Inject
    private FmuEventService fmuEventService;
    

	@Override
	public ArendeId createEavrop(CreateEavropCommand aCommand) {
		log.debug("Creating Eavrop {} ", aCommand.getArendeId().toString());
		ArendeId arendeId = fmuOrderingService.createEavrop(aCommand);
		
		fmuEventService.publishEavropCreatedEvent(arendeId);
		return arendeId;
	}
	
	@Override
	public void addBookingDeviationResponse(AddBookingDeviationResponseCommand aCommand){
		log.debug("Adding booking deviation response {} ", aCommand.getArendeId().toString());
		EavropId eavropId =  eavropBookingService.addBookingDeviationResponse(aCommand);
		
		BookingDeviationResponseType responseType = aCommand.getResponseType();
		if(BookingDeviationResponseType.RESTART.equals(responseType)){
			fmuEventService.publishEavropRestartedByBestallareEvent(eavropId);
		
		}else if(BookingDeviationResponseType.STOP.equals(responseType)) {
			fmuEventService.publishEavropClosedByBestallareEvent(eavropId);	
		}
	}
	
	@Override
	public void addReceivedExternalDocument(AddReceivedExternalDocumentsCommand aCommand){
		log.debug("Adding received external document to Eavrop {} ", aCommand.getArendeId().toString());
		
		boolean isStartDocuments  = eavropDocumentService.addReceivedExternalDocument(aCommand);
		
		fmuEventService.publishDocumentsSentByBestallareEvent(aCommand.getArendeId(), aCommand.getDocumentsSentDateTime());
		
		//If receiving these documents triggers a start of the Eavrop
		if(isStartDocuments){
			fmuEventService.publishEavropStartEvent(aCommand.getArendeId());	
		}
	}
	
	@Override
	public void addIntygSentInformation(AddIntygSentCommand aCommand){
		log.debug("Adding intyg sent information to Eavrop {} ", aCommand.getArendeId().toString());
		EavropId eavropId = eavropIntygService.addIntygSentInformation(aCommand);
		fmuEventService.publishIntygSentEvent(eavropId, aCommand.getArendeId(), aCommand.getIntygSentDateTime());
	}

	@Override
	public void addIntygComplementRequestInformation(AddIntygComplementRequestCommand aCommand){
		log.debug("Adding intyg complement request information to Eavrop {} ", aCommand.getArendeId().toString());
		EavropId eavropId = eavropIntygService.addIntygComplementRequestInformation(aCommand);
		fmuEventService.publishIntygComplemetsRequestedFromBestallareEvent(eavropId, aCommand.getIntygComplementRequestDateTime());
	}
	
	@Override
	public void addIntygApprovedInformation(AddIntygApprovedCommand aCommand){
		log.debug("Adding intyg approved information to Eavrop {} ", aCommand.getArendeId().toString());
		EavropId eavropId = eavropIntygService.addIntygApprovedInformation(aCommand);
		fmuEventService.publishIntygApprovedByBestallareEvent(eavropId, aCommand.getIntygApprovedDateTime());
	}

	@Override
	public void approveEavrop(ApproveEavropCommand aCommand) {
		log.debug("Approving Eavrop {} ", aCommand.getArendeId().toString());
		EavropId eavropId = eavropApprovalService.approveEavrop(aCommand);
		fmuEventService.publishEavropApprovedByBestallareEvent(eavropId, aCommand.getApproveDateTime());
	}

	@Override
	public void approveEavropCompensation(ApproveEavropCompensationCommand aCommand) {
		log.debug("Approving compensation for Eavrop {} ", aCommand.getArendeId().toString());
		EavropId eavropId = eavropApprovalService.approveEavropCompensation(aCommand);
		fmuEventService.publishEavropCompensationApprovedByBestallareEvent(eavropId, aCommand.getApproveDateTime());
	}
}
