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
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.EavropNoteService;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
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
import se.inera.fmu.domain.model.eavrop.Interpreter;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.eavrop.note.NoteType;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.person.Person;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.model.systemparameter.Configuration;
import se.inera.fmu.interfaces.managing.dtomapper.AllEventsDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.BestallningDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.CompletedEavropDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.EavropDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.NoteDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.OrderDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.PagaendeDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.PatientDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.ReceivedDocumentDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.RequestedDocumentDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.UtredningDTOMapper;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.dto.AddNoteRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingModificationRequestDTO;
import se.inera.fmu.interfaces.managing.rest.dto.BookingRequestDTO;
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

/**
 * Created by Rasheed on 7/7/14.
 *
 * Application Service for managing FMU process
 */
@SuppressWarnings("all")
@Service
@Validated
@Transactional
@Slf4j
public class FmuOrderingServiceImpl implements FmuOrderingService {

	private final EavropRepository eavropRepository;
	private final InvanareRepository invanareRepository;
	private final DomainEventPublisher domainEventPublisher;
	private final Configuration configuration;
	private final LandstingRepository landstingRepository;
	private final CurrentUserService currentUserService;
	private final VardgivarenhetRepository vardgivarEnhetRepository;
	private final FmuListService fmuListService;
	private final EavropBookingService bookingService;
	private final EavropNoteService noteService;

	/**
	 *
	 * @param eavropRepository
	 * @param invanareRepository
	 * @param asyncEventBus
	 */
	@Inject
	public FmuOrderingServiceImpl(final EavropRepository eavropRepository,
			final InvanareRepository invanareRepository, final Configuration configuration,
			final DomainEventPublisher domainEventPublisher,
			final LandstingRepository landstingRepository, final CurrentUserService currentUser,
			final VardgivarenhetRepository vardgivarEnhetRepository,
			final FmuListService fmuListService, final EavropBookingService bookingService,
			final EavropNoteService noteService) {
		this.eavropRepository = eavropRepository;
		this.invanareRepository = invanareRepository;
		this.configuration = configuration;
		this.domainEventPublisher = domainEventPublisher;
		this.landstingRepository = landstingRepository;
		this.currentUserService = currentUser;
		this.vardgivarEnhetRepository = vardgivarEnhetRepository;
		this.fmuListService = fmuListService;
		this.bookingService = bookingService;
		this.noteService = noteService;
	}

	/**
	 * Creates a an eavrop.
	 *
	 * @param aCommand
	 *            : CreateEavropCommand
	 * @return arendeId
	 */
	@Override
	public ArendeId createEavrop(CreateEavropCommand aCommand) {

		EavropProperties props = getEavropProperties();

        Landsting landsting = landstingRepository.findByLandstingCode(aCommand.getLandstingCode());
        if(landsting == null){
            throw new IllegalArgumentException(String.format("Landsting with id %s does not exist", aCommand.getArendeId()));
        }

		Eavrop eavrop = EavropBuilder.eavrop()
                .withArendeId(aCommand.getArendeId())
				.withUtredningType(aCommand.getUtredningType())
                .withInvanare(aCommand.getInvanare())
				.withLandsting(landsting)
				.withBestallaradministrator(aCommand.getBestallaradministrator())
                .withInterpreter(aCommand.getInterpreter())
				.withEavropProperties(props).withDescription(aCommand.getDescription())
				.withUtredningFocus(aCommand.getUtredningFocus())
				.withAdditionalInformation(aCommand.getAdditionalInformation())
				.withPriorMedicalExamination(aCommand.getPriorMedicalExamination())
                .build();

		eavrop = eavropRepository.save(eavrop);

		log.debug(String.format("eavrop created :: %s", eavrop));

		// Publish an event to notify the interested listeners/subscribers that
		// an eavrop has been created.
		domainEventPublisher.post(new EavropCreatedEvent(eavrop.getEavropId()));

		return eavrop.getArendeId();
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

	@Override
	public EavropPageDTO getOverviewEavrops(long fromDate, long toDate, OverviewEavropStates state,
			Pageable paginationSpecs) {
		User currentUSer = this.currentUserService.getCurrentUser();
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);

		switch (currentUSer.getActiveRole()) {
		case ROLE_SAMORDNARE:
			if (currentUSer.getLandstingCode() == null)
				return null;
			Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(
					currentUSer.getLandstingCode()));

			switch (state) {
			case NOT_ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllNotAcceptedEavropByLandstingAndDateTimeOrdered(landsting,
								startDate, endDate, paginationSpecs), new BestallningDTOMapper());
			case ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllOngoingEavropByLandstingAndDateTimeStarted(landsting,
								startDate.toLocalDate(), endDate.toLocalDate(), paginationSpecs), 
								new PagaendeDTOMapper());
			case COMPLETED:
				return constructOverviewDTO(this.fmuListService
						.findAllCompletedEavropByLandstingAndDateTimeSent(landsting, startDate,
								endDate, paginationSpecs), new CompletedEavropDTOMapper());
			default:
				return null;
			}
		case ROLE_UTREDARE:
			if (currentUSer.getVardenhetHsaId() == null) {
				return null;
			}
			Vardgivarenhet vardgivarenhet = this.fmuListService
					.findVardgivarenhetByHsaId(new HsaId(currentUSer.getVardenhetHsaId()));

			switch (state) {
			case NOT_ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered(vardgivarenhet,
								startDate, endDate, paginationSpecs), new BestallningDTOMapper());
			case ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllOngoingEavropByVardgivarenhetAndDateTimeStarted(vardgivarenhet,
								startDate.toLocalDate(), endDate.toLocalDate(), paginationSpecs), 
								new PagaendeDTOMapper());
			case COMPLETED:
				return constructOverviewDTO(this.fmuListService
						.findAllCompletedEavropByVardgivarenhetAndDateTimeSent(vardgivarenhet,
								startDate, endDate, paginationSpecs), new CompletedEavropDTOMapper());
			default:
				return null;
			}
		default:
			return null;
		}
	}

	private EavropPageDTO constructOverviewDTO(Page<Eavrop> eavrops, EavropDTOMapper eavropMapper) {
		List<EavropDTO> data = new ArrayList<EavropDTO>();
		for (Eavrop eavrop : eavrops.getContent()) {
			data.add(eavropMapper.map(eavrop));
		}

		EavropPageDTO retval = new EavropPageDTO();
		retval.setEavrops(data).setTotalElements(eavrops.getTotalElements());

		return retval;
	}

	@Override
	public List<HandelseDTO> getEavropEvents(String eavropId) {
		UtredningDTOMapper mapper = new UtredningDTOMapper();
		Eavrop eavrop = this.eavropRepository.findByEavropId(new EavropId(eavropId));
		if (eavrop != null)
			return mapper.map(eavrop);

		return null;
	}

	protected Eavrop getEavropForUser(EavropId id) {
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop result = null;

		if (currentUser.getActiveRole() == Role.ROLE_SAMORDNARE) {
			Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(
					currentUser.getLandstingCode()));
			result = this.eavropRepository.findByEavropIdAndLandsting(id, landsting);
		} else if (currentUser.getActiveRole() == Role.ROLE_UTREDARE) {
			Vardgivarenhet ve = this.vardgivarEnhetRepository.findByHsaId(new HsaId(currentUser
					.getVardenhetHsaId()));
			result = this.eavropRepository.findByEavropIdAndVardgivare(id, ve);
		} else {
			throw new IllegalStateException("User has no active role");
		}

		return result;
	}

	@Override
	public AllEventsDTO getAllEvents(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);

		return new AllEventsDTOMapper().map(eavropForUser);
	}

	@Override
	public OrderDTO getOrderInfo(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		return new OrderDTOMapper().map(eavropForUser);
	}

	@Override
	public List<ReceivedDocumentDTO> getReceivedDocuments(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		List<ReceivedDocumentDTO> result = new ArrayList<>();
		ReceivedDocumentDTOMapper mapper = new ReceivedDocumentDTOMapper();

		for (ReceivedDocument doc : eavropForUser.getReceivedDocuments()) {
			result.add(mapper.map(doc));
		}

		return result;
	}

	@Override
	public List<RequestedDocumentDTO> getRequestedDocuments(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		List<RequestedDocumentDTO> result = new ArrayList<>();
		RequestedDocumentDTOMapper mapper = new RequestedDocumentDTOMapper();

		for (RequestedDocument doc : eavropForUser.getRequestedDocuments()) {
			result.add(mapper.map(doc, eavropForUser));
		}

		return result;
	}

	@Override
	public List<NoteDTO> getNotes(EavropId eavropId) {
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop eavropForUser = getEavropForUser(eavropId);
		NoteDTOMapper mapper = new NoteDTOMapper();
		List<NoteDTO> result = new ArrayList<>();
		if (eavropForUser == null)
			return result;
		for (Note n : eavropForUser.getAllNotes()) {
			result.add(mapper.map(n, currentUser));
		}

		return result;
	}

	private Person createPersonObjectFromCurrentUser() {
		User currentUser = currentUserService.getCurrentUser();
		String name = String.format("%s %s", currentUser.getFirstName(),
				currentUser.getMiddleAndLastName());
		HoSPerson p = new HoSPerson(getHsaId(currentUser), name, currentUser.getActiveRole().toString(),
				currentUser.getOrganization(), currentUser.getUnit());

		return p;
	}

	@Override
	public void addReceivedDocuments(EavropId eavropId, ReceivedDocumentDTO doc) {
		Eavrop eavropForUser = getEavropForUser(eavropId);

		Person p = createPersonObjectFromCurrentUser();

		ReceivedDocument receivedDocument = new ReceivedDocument(doc.getName(), p, false);
		eavropForUser.addReceivedDocument(receivedDocument);
	}

	@Override
	public void addRequestedDocuments(EavropId eavropId, RequestedDocumentDTO doc) {
		Eavrop eavropForUser = getEavropForUser(eavropId);

		Person p = createPersonObjectFromCurrentUser();
		Note requestNote = new Note(NoteType.DOCUMENT_REQUEST, doc.getComment(), p);
		RequestedDocument requestedDocument = new RequestedDocument(doc.getName(), p, requestNote);
		eavropForUser.addRequestedDocument(requestedDocument);
	}

	@Override
	public void addBooking(BookingRequestDTO changeRequestDto) {
		User currentUser = this.currentUserService.getCurrentUser();
		BookingType type = changeRequestDto.getBookingType();
		Long bookingDateMilis = changeRequestDto.getBookingDate();
		TimeDTO startTime = changeRequestDto.getBookingStartTime();
		TimeDTO endTime = changeRequestDto.getBookingEndTime();
		DateTime sDateTime = new DateTime(bookingDateMilis).withTime(startTime.getHour(),
				startTime.getMinute(), 0, 0);
		DateTime eDateTime = new DateTime(bookingDateMilis).withTime(endTime.getHour(),
				endTime.getMinute(), 0, 0);

		CreateBookingCommand command = new CreateBookingCommand(new EavropId(
				changeRequestDto.getEavropId()), type, sDateTime, eDateTime, getHsaId(currentUser),
				changeRequestDto.getPersonName(), changeRequestDto.getPersonRole(),
				changeRequestDto.getPersonOrganisation(), // TODO Where does this info comes from ?
				changeRequestDto.getPersonUnit(), // TODO Where does this infocomes from ?
				changeRequestDto.getAdditionalService(), changeRequestDto.getUseInterpreter());

		this.bookingService.createBooking(command);
	}

	@Override
	public void modifyBooking(BookingModificationRequestDTO changeRequestData) {
		User currentUser = this.currentUserService.getCurrentUser();
		ChangeBookingStatusCommand command = new ChangeBookingStatusCommand(new EavropId(
				changeRequestData.getEavropId()), new BookingId(changeRequestData.getBookingId()),
				changeRequestData.getBookingStatus(), changeRequestData.getComment(),
				getHsaId(currentUser), currentUser.getFirstName() + " " + currentUser.getMiddleAndLastName(), currentUser
						.getActiveRole().name(), getUserOrganisation(currentUser),
				getUserUnit(currentUser));
		this.bookingService.changeBookingStatus(command);
	}

	@Override
	public void modifyTolkBooking(TolkBookingModificationRequestDTO changeRequestData) {
		User currentUser = this.currentUserService.getCurrentUser();
		ChangeInterpreterBookingStatusCommand command = new ChangeInterpreterBookingStatusCommand(
				new EavropId(changeRequestData.getEavropId()), new BookingId(
						changeRequestData.getBookingId()), changeRequestData.getBookingStatus(),
				changeRequestData.getComment(), getHsaId(currentUser), getUserName(currentUser), currentUser
						.getActiveRole().name(), getUserOrganisation(currentUser),
				getUserUnit(currentUser));
		this.bookingService.changeInterpreterBookingStatus(command);
	}

	@Override
	public void addNote(AddNoteRequestDTO addRequest) {
		User currentUser = this.currentUserService.getCurrentUser();
		AddNoteCommand command = new AddNoteCommand(new EavropId(addRequest.getEavropId()),
				addRequest.getText(), getHsaId(currentUser), getUserName(currentUser), 
				currentUser.getActiveRole().name().toLowerCase(), 
				getUserOrganisation(currentUser),
				getUserUnit(currentUser));
		this.noteService.addNote(command);
	}
	
	@Override
	public void removeNote(String eavropId, String noteId) {
		User currentUser = this.currentUserService.getCurrentUser();
		RemoveNoteCommand command = new RemoveNoteCommand(
				new EavropId(eavropId), 
				new NoteId(noteId), 
				new HsaId(currentUser.getHsaId()));
		this.noteService.removeNote(command);
	}

	/**
	 * Get the HsaId of the user
	 * @param currentUser The currently logged in user
	 * @return The user's HsaId
	 */
	private HsaId getHsaId(User currentUser) {
		if(!StringUtils.isBlankOrNull(currentUser.getHsaId())){
			return  new HsaId(currentUser.getHsaId());
		}
		return null;
	}

	/**
	 * Get the name of the user
	 * @param currentUser The currently logged in user
	 * @return The user's name
	 */
	private String getUserName(User currentUser) {
		return currentUser.getFirstName() + " "
				+ currentUser.getMiddleAndLastName();
	}

	/**
	 * Get the name of the organisation the user belong to based on their active
	 * role
	 * 
	 * @param user
	 * @return The name of the organisation the user belong to
	 */
	private String getUserOrganisation(User user) {
		String retval = null;
		switch (user.getActiveRole()) {
		case ROLE_SAMORDNARE:
			Landsting landsting = this.landstingRepository
					.findByLandstingCode(new LandstingCode(user.getLandstingCode()));
			retval = landsting.getName();
			break;
		case ROLE_UTREDARE:
			Vardgivarenhet vEnhet = this.vardgivarEnhetRepository.findByHsaId(new HsaId(user.getVardenhetHsaId()));
			retval = vEnhet.getVardgivare().getName();
			break;
		default:
			break;
		}
		
		return retval;
	}

	/**
	 * Get the name of the unit the user belong to based on their active role
	 * 
	 * @param user
	 * @return The name of the unit the user belong to
	 */
	private String getUserUnit(User user) {
		String retval = null;
		switch (user.getActiveRole()) {
		case ROLE_SAMORDNARE:
			retval = null;
			break;
		case ROLE_UTREDARE:
			Vardgivarenhet vEnhet = this.vardgivarEnhetRepository.findByHsaId(new HsaId(user.getVardenhetHsaId()));
			retval = vEnhet.getUnitName();
			break;
		default:
			break;
		}
		
		return retval;
	}

	@Override
	public PatientDTO getPatientInfo(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		Invanare invanare = eavropForUser.getInvanare();
		
		PatientDTOMapper mapper = new PatientDTOMapper();
		PatientDTO dto = mapper.map(eavropForUser, currentUserService.getCurrentUser().getActiveRole() == Role.ROLE_UTREDARE);
		
		return dto;
	}

	@Override
	public EavropDTO getEavrop(EavropId eavropId) {
		EavropDTOMapper mapper = new EavropDTOMapper();
		Eavrop eavropForUser = getEavropForUser(eavropId);
		return mapper.map(eavropForUser, new EavropDTO());
	}

}
