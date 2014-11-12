package se.inera.fmu.application.impl;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropDocumentService;
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.document.DocumentSentByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;

/**
 * Service for handling eavrop assignments
 * 
 * @author 
 *
 */
@Service
@Validated
@Transactional
public class EavropDocumentServiceImpl implements EavropDocumentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EavropRepository eavropRepository;
    private final DomainEventPublisher domainEventPublisher;
    

    /**
     * Constructor
     * @param eavropRepository
     * @param domainEventPublisher
     */
	@Inject
	public EavropDocumentServiceImpl(EavropRepository eavropRepository, DomainEventPublisher domainEventPublisher) {
		this.eavropRepository = eavropRepository;
		this.domainEventPublisher = domainEventPublisher;
	}

	@Override
	public void addReceivedExternalDocument(AddReceivedExternalDocumentCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		LocalDate startDate = eavrop.getStartDate();
		
		Bestallaradministrator person = new Bestallaradministrator(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit(), aCommand.getPersonPhone(), aCommand.getPersonEmail());
		ReceivedDocument document = new ReceivedDocument(aCommand.getDocumentName(), person, Boolean.TRUE);
		
		eavrop.addReceivedDocument(document);
		
		if(eavrop.getStartDate() != null && !eavrop.getStartDate().equals(startDate)){
			handleDocumentsSentByBestallare(eavrop.getEavropId(), document.getDocumentDateTime());
		}

	}

	@Override
	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		HoSPerson person = new HoSPerson(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		ReceivedDocument document = new ReceivedDocument(aCommand.getDocumentName(), person, Boolean.FALSE);
		eavrop.addReceivedDocument(document);
	}
	
	@Override
	public void addRequestedDocument(AddRequestedDocumentCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		HoSPerson person = new HoSPerson(aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		RequestedDocument document = new RequestedDocument(aCommand.getDocumentName(), person);
	}

	private Eavrop getEavropByEavropId(EavropId eavropId) throws EntityNotFoundException{
		Eavrop eavrop = this.eavropRepository.findByEavropId(eavropId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop %s not found", eavropId.toString()));
		}
		return eavrop;
	}

	private Eavrop getEavropByArendeId(ArendeId arendeId){
		Eavrop eavrop = eavropRepository.findByArendeId(arendeId);
		if(eavrop==null){
			throw new EntityNotFoundException(String.format("Eavrop with ArendeId %s not found", arendeId.toString()));
		}
		return eavrop;
	}

	private DomainEventPublisher getDomainEventPublisher(){
		return this.domainEventPublisher;
	}

	private void handleDocumentsSentByBestallare(EavropId eavropId, DateTime dateTimeDocumentsSent){
		//TODO: dont know if this event should be created... 
		DocumentSentByBestallareEvent event = new DocumentSentByBestallareEvent(eavropId, dateTimeDocumentsSent);
        if(log.isDebugEnabled()){
        	log.debug(String.format("DocumentSentByBestallareEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
}
