package se.inera.fmu.application.impl;

import java.util.List;

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
import se.inera.fmu.application.impl.command.AddReceivedExternalDocumentsCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.document.DocumentRequestedEvent;
import se.inera.fmu.domain.model.eavrop.document.DocumentSentByBestallareEvent;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
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
	public void addReceivedExternalDocument(AddReceivedExternalDocumentsCommand aCommand) {
		Eavrop eavrop = getEavropByArendeId(aCommand.getArendeId());
		LocalDate startDate = eavrop.getStartDate();
		DateTime documentsSentFromBestallareDateTime = (aCommand.getDocumentsSentDateTime()!=null)?aCommand.getDocumentsSentDateTime():DateTime.now();
		
		Bestallaradministrator person = aCommand.getBestallaradministrator();
		
		List<String> documentNames = aCommand.getDocumentNames();
		
		
		if(documentNames != null && !documentNames.isEmpty()){
			for (String documentName : documentNames) {
				if(!StringUtils.isBlankOrNull(documentName)){
					ReceivedDocument document = new ReceivedDocument(documentsSentFromBestallareDateTime, documentName, person, Boolean.TRUE);
					eavrop.addReceivedDocument(document);
				}
			}	
		}else{
			if(eavrop.getStartDate()==null){
				eavrop.setDateTimeDocumentsSentFromBestallare(documentsSentFromBestallareDateTime);
			}
		}
		
		if(eavrop.getStartDate() != null && !eavrop.getStartDate().equals(startDate)){
			handleDocumentsSentByBestallare(eavrop.getEavropId(), documentsSentFromBestallareDateTime);
		}
	}

	@Override
	public void addReceivedInternalDocument(AddReceivedInternalDocumentCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		HoSPerson person = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		ReceivedDocument document = new ReceivedDocument(aCommand.getDocumentName(), person, Boolean.FALSE);
		eavrop.addReceivedDocument(document);
	}
	
	@Override
	public void addRequestedDocument(AddRequestedDocumentCommand aCommand) {
		Eavrop eavrop = getEavropByEavropId(aCommand.getEavropId());
		HoSPerson person = null;
		Note requestNote = null;
		
		if(StringUtils.isBlankOrNull(aCommand.getDocumentName())){
			//TODO: Throw something
		}
		
		if(!StringUtils.isBlankOrNull(aCommand.getPersonName()) || aCommand.getPersonHsaId()!=null){
			person = new HoSPerson(aCommand.getPersonHsaId(), aCommand.getPersonName(),  aCommand.getPersonRole(), aCommand.getPersonOrganisation(), aCommand.getPersonUnit());
		}
		
		if(!StringUtils.isBlankOrNull(aCommand.getRequestNote())){
			requestNote  = new Note(NoteType.DOCUMENT_REQUEST, aCommand.getRequestNote(), person);
		}
		
		RequestedDocument requestedDocument = new RequestedDocument(aCommand.getDocumentName(), person, requestNote);
		
		eavrop.addRequestedDocument(requestedDocument);
		log.debug(String.format("RequestedDocument created :: %s", requestedDocument));
		
		handleDocumentRequested(aCommand.getEavropId(), requestedDocument.getId());
		
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
	
	private void handleDocumentRequested(EavropId eavropId, String documentId){
		DocumentRequestedEvent event = new DocumentRequestedEvent(eavropId, documentId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("DocumentRequestedEvent created :: %s", event.toString()));
        }
		getDomainEventPublisher().post(event);
	}
}
