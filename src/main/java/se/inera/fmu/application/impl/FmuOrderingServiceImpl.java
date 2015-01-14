package se.inera.fmu.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.EavropDocumentService;
import se.inera.fmu.application.EavropNoteService;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.EavropCreatedEvent;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.intyg.IntygSentEvent;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.personal.HoSPersonalRepository;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.systemparameter.Configuration;
import se.inera.fmu.interfaces.managing.dtomapper.AllEventsDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.BestallningDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.CompensationDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.CompletedEavropDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.EavropDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.NoteDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.OrderDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.PagaendeDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.PatientDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.ReceivedDocumentDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.RequestedDocumentDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.UtredningDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.VardgivarenhetDTOMapper;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.dto.AddNoteRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.CompensationDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.HandelseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.NoteDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;
import se.inera.fmu.interfaces.managing.rest.dto.PatientDTO;
import se.inera.fmu.interfaces.managing.rest.dto.ReceivedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.RequestedDocumentDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TimeDTO;
import se.inera.fmu.interfaces.managing.rest.dto.TolkBookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.VardgivarenhetDTO;

/**
 * Created by Rasheed on 7/7/14.
 *
 * Application Service for managing FMU process
 */
@SuppressWarnings("all")
@Service
@Validated
@Slf4j
public class FmuOrderingServiceImpl implements FmuOrderingService {

	private final EavropRepository eavropRepository;
	private final InvanareRepository invanareRepository;
	private final DomainEventPublisher domainEventPublisher;
	private final Configuration configuration;
	private final LandstingRepository landstingRepository;

	/**
	 *
	 * @param eavropRepository
	 * @param invanareRepository
	 * @param configuration
	 * @param domainEventPublisher
	 * @param landstingRepository
	 */
	@Inject
	public FmuOrderingServiceImpl(final EavropRepository eavropRepository,
								  final InvanareRepository invanareRepository, 
								  final Configuration configuration,
								  final DomainEventPublisher domainEventPublisher,
								  final LandstingRepository landstingRepository) {
		this.eavropRepository = eavropRepository;
		this.invanareRepository = invanareRepository;
		this.configuration = configuration;
		this.domainEventPublisher = domainEventPublisher;
		this.landstingRepository = landstingRepository;
	}

	/**
	 * Creates a an eavrop.
	 *
	 * @param aCommand  CreateEavropCommand
	 * @return arendeId
	 */
	@Override
	public ArendeId createEavrop(CreateEavropCommand aCommand) {
		Eavrop eavrop = saveEavrop(aCommand);
		
		handleEavropCreated(eavrop.getEavropId(), eavrop.getArendeId());
		
		return eavrop.getArendeId();
		
	}

	@Transactional
	private Eavrop saveEavrop(CreateEavropCommand aCommand) {
		EavropProperties props = getEavropProperties();

		Landsting landsting = landstingRepository.findByLandstingCode(aCommand.getLandstingCode());
		if (landsting == null) {
			throw new IllegalArgumentException(String.format("Landsting with id %s does not exist",	aCommand.getArendeId()));
		}

		if (eavropRepository.findByArendeId(aCommand.getArendeId()) != null) {
			throw new IllegalArgumentException(String.format(
					"Eavrop with arendeId %s already exist", aCommand.getArendeId()));
		}

		Eavrop eavrop = EavropBuilder.eavrop().withArendeId(aCommand.getArendeId())
				.withUtredningType(aCommand.getUtredningType())
				.withInvanare(aCommand.getInvanare()).withLandsting(landsting)
				.withBestallaradministrator(aCommand.getBestallaradministrator())
				.withInterpreter(aCommand.getInterpreter()).withEavropProperties(props)
				.withDescription(aCommand.getDescription())
				.withUtredningFocus(aCommand.getUtredningFocus())
				.withAdditionalInformation(aCommand.getAdditionalInformation())
				.withPriorMedicalExamination(aCommand.getPriorMedicalExamination()).build();

		eavrop = eavropRepository.save(eavrop);
		log.debug(String.format("eavrop with arendeId  %s created", eavrop.getArendeId()));

		return eavrop;
	}
	
	/**
	 * Publish an event to notify the interested listeners/subscribers that an eavrop has been created.
	 * 
	 * @param eavropId
	 * @param arendeId
	 */
	private void handleEavropCreated(EavropId eavropId, ArendeId arendeId){
		EavropCreatedEvent event = new EavropCreatedEvent(eavropId, arendeId);
        if(log.isDebugEnabled()){
        	log.debug(String.format("EavropCreatedEvent created :: %s", event.toString()));
        }
		domainEventPublisher.post(event);
	}

	private EavropProperties getEavropProperties() {
		int startDateOffset = getConfiguration().getInteger(
				Configuration.KEY_EAVROP_START_DATE_OFFSET, 3);
		int acceptanceValidLength = getConfiguration().getInteger(
				Configuration.KEY_EAVROP_ACCEPTANCE_VALID_LENGTH, 5);
		int assessmentValidLength = getConfiguration().getInteger(
				Configuration.KEY_EAVROP_ASSESSMENT_VALID_LENGTH, 25);
		int completionValidLength = getConfiguration().getInteger(
				Configuration.KEY_EAVROP_COMPLETION_VALID_LENGTH, 10);

		return new EavropProperties(startDateOffset, acceptanceValidLength, assessmentValidLength,
				completionValidLength);
	}

	private Configuration getConfiguration() {
		return this.configuration;
	}



}
