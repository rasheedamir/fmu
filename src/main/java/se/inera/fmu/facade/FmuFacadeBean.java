package se.inera.fmu.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.EavropAssignmentService;
import se.inera.fmu.application.EavropBookingService;
import se.inera.fmu.application.EavropDocumentService;
import se.inera.fmu.application.EavropNoteService;
import se.inera.fmu.application.FmuEventService;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.impl.command.AcceptEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.AddNoteCommand;
import se.inera.fmu.application.impl.command.AddReceivedInternalDocumentCommand;
import se.inera.fmu.application.impl.command.AddRequestedDocumentCommand;
import se.inera.fmu.application.impl.command.AssignEavropCommand;
import se.inera.fmu.application.impl.command.ChangeBookingStatusCommand;
import se.inera.fmu.application.impl.command.ChangeInterpreterBookingStatusCommand;
import se.inera.fmu.application.impl.command.CreateBookingCommand;
import se.inera.fmu.application.impl.command.RejectEavropAssignmentCommand;
import se.inera.fmu.application.impl.command.RemoveNoteCommand;
import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.booking.BookingId;
import se.inera.fmu.domain.model.eavrop.booking.BookingType;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.document.RequestedDocument;
import se.inera.fmu.domain.model.eavrop.note.Note;
import se.inera.fmu.domain.model.eavrop.note.NoteId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
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

@Slf4j
@Service
public class FmuFacadeBean  implements FmuFacade {

	@Inject 
	private FmuListService fmuListService;
	
	@Inject 
	private CurrentUserService currentUserService;
	
	@Inject 
	private EavropDocumentService eavropDocumentService;
	
	@Inject 
	private EavropBookingService eavropBookingService;
	
	@Inject 
	private EavropNoteService eavropNoteService;

	@Inject 
	private EavropAssignmentService eavropAssignmentService;
	
	@Inject
	private FmuEventService fmuEventService;

	
	@Override
	@Transactional(readOnly=true)
	public EavropPageDTO getOverviewEavrops(long fromDate, long toDate, OverviewEavropStates state,
			Pageable paginationSpecs) {
		
		log.debug("Get EavropDTOs from state {} ",state.toString());
		User currentUser = this.currentUserService.getCurrentUser();
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);

		switch (currentUser.getActiveRole()) {
		case ROLE_SAMORDNARE:
			return  getOverviewEavropsForSamordnare(currentUser, startDate, endDate, state, paginationSpecs);
		case ROLE_UTREDARE:
			return getOverviewEavropsForUtredare(currentUser, startDate, endDate, state, paginationSpecs);
		default:
			return null;
		}

	}
	
	private EavropPageDTO getOverviewEavropsForSamordnare(User currentUser, DateTime startDate, DateTime endDate, OverviewEavropStates state,
			Pageable paginationSpecs){
		if (currentUser.getLandstingCode() == null)
			return null;
		log.debug("Get Landsting by code {} ",currentUser.getLandstingCode().toString());
		Landsting landsting = this.fmuListService.findLandstingByLandstingCode(new LandstingCode(currentUser.getLandstingCode()));

		switch (state) {
		case NOT_ACCEPTED:
			log.debug("findAllNotAcceptedEavropByLandstingAndDateTimeOrder Landsting:{} start:{} end:{}  ",currentUser.getLandstingCode().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllNotAcceptedEavropByLandstingAndDateTimeOrdered(
							landsting, startDate, endDate, paginationSpecs),
					new BestallningDTOMapper());
		case ACCEPTED:
			log.debug("findAllOngoingEavropByLandstingAndDateTimeStarted Landsting:{} start:{} end:{}  ",currentUser.getLandstingCode().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllOngoingEavropByLandstingAndDateTimeStarted(
							landsting, startDate.toLocalDate(), endDate.toLocalDate(),
							paginationSpecs), new PagaendeDTOMapper());
		case COMPLETED:
			log.debug("findAllCompletedEavropByLandstingAndDateTimeSent Landsting:{} start:{} end:{}  ",currentUser.getLandstingCode().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllCompletedEavropByLandstingAndDateTimeSent(
							landsting, startDate, endDate, paginationSpecs),
					new CompletedEavropDTOMapper());
		default:
			return null;
		}

	}
	
	private EavropPageDTO getOverviewEavropsForUtredare(User currentUser, DateTime startDate, DateTime endDate, OverviewEavropStates state,
			Pageable paginationSpecs){
		if (currentUser.getVardenhetHsaId() == null) {
			return null;
		}
		log.debug("Get Vardgivarenhet by hsaId {} ",currentUser.getVardenhetHsaId().toString());
		Vardgivarenhet vardgivarenhet = this.fmuListService.findVardgivarenhetByHsaId(new HsaId(currentUser.getVardenhetHsaId()));

		switch (state) {
		case NOT_ACCEPTED:
			log.debug("findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered HsaId:{} start:{} end:{}  ",currentUser.getVardenhetHsaId().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered(
							vardgivarenhet, startDate, endDate, paginationSpecs),
					new BestallningDTOMapper());
		case ACCEPTED:
			log.debug("findAllOngoingEavropByVardgivarenhetAndDateTimeStarted HsaId:{} start:{} end:{}  ",currentUser.getVardenhetHsaId().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllOngoingEavropByVardgivarenhetAndDateTimeStarted(
							vardgivarenhet, startDate.toLocalDate(), endDate.toLocalDate(),
							paginationSpecs), new PagaendeDTOMapper());
		case COMPLETED:
			log.debug("findAllCompletedEavropByVardgivarenhetAndDateTimeSent HsaId:{} start:{} end:{}  ",currentUser.getVardenhetHsaId().toString(), startDate.toString(), endDate.toString() );
			return constructOverviewDTO(
					this.fmuListService.findAllCompletedEavropByVardgivarenhetAndDateTimeSent(
							vardgivarenhet, startDate, endDate, paginationSpecs),
					new CompletedEavropDTOMapper());
		default:
			return null;
		}
		
	}
	
	private EavropPageDTO constructOverviewDTO(Page<Eavrop> eavrops, EavropDTOMapper eavropMapper) {
		List<EavropDTO> eavropDTOs = new ArrayList<EavropDTO>();
		for (Eavrop eavrop : eavrops.getContent()) {
			eavropDTOs.add(eavropMapper.map(eavrop));
		}

		EavropPageDTO eavropPageDto = new EavropPageDTO();
		eavropPageDto.setEavrops(eavropDTOs).setTotalElements(eavrops.getTotalElements());

		return eavropPageDto;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<HandelseDTO> getEavropEvents(String eavropId) {
		UtredningDTOMapper utredningMapper = new UtredningDTOMapper();
		Eavrop eavrop = this.fmuListService.findByEavropId(new EavropId(eavropId));
		if (eavrop != null){
			return utredningMapper.map(eavrop);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public AllEventsDTO getAllEvents(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);

		return new AllEventsDTOMapper().map(eavropForUser);
	}
	
	@Override
	@Transactional(readOnly=true)
	public OrderDTO getOrderInfo(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		return new OrderDTOMapper().map(eavropForUser);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ReceivedDocumentDTO> getReceivedDocuments(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		List<ReceivedDocumentDTO> receivedDocumentDTOs = new ArrayList<>();
		ReceivedDocumentDTOMapper receivedDocumentMapper = new ReceivedDocumentDTOMapper();

		for (ReceivedDocument doc : eavropForUser.getReceivedDocuments()) {
			receivedDocumentDTOs.add(receivedDocumentMapper.map(doc));
		}

		return receivedDocumentDTOs;
	}

	@Override
	@Transactional(readOnly=true)
	public List<RequestedDocumentDTO> getRequestedDocuments(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		List<RequestedDocumentDTO> requestedDocumentDTOs = new ArrayList<>();
		RequestedDocumentDTOMapper requestedDocumentMapper = new RequestedDocumentDTOMapper();

		for (RequestedDocument doc : eavropForUser.getRequestedDocuments()) {
			requestedDocumentDTOs.add(requestedDocumentMapper.map(doc, eavropForUser));
		}

		return requestedDocumentDTOs;
	}


	@Override
	@Transactional(readOnly=true)
	public List<NoteDTO> getNotes(EavropId eavropId) {
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop eavropForUser = getEavropForUser(eavropId);
		NoteDTOMapper noteMapper = new NoteDTOMapper();
		List<NoteDTO> noteDTOs = new ArrayList<>();
		if (eavropForUser == null){
			return noteDTOs;
		}
			
		for (Note n : eavropForUser.getAllNotes()) {
			noteDTOs.add(noteMapper.map(n, currentUser));
		}

		return noteDTOs;
	}


	@Override
	public void addReceivedDocuments(EavropId eavropId, ReceivedDocumentDTO doc) {
		this.eavropDocumentService.addReceivedInternalDocument(createAddReceivedInternalDocumentCommand(eavropId, doc));
	}
	
	@Transactional(readOnly=true)
	private AddReceivedInternalDocumentCommand createAddReceivedInternalDocumentCommand(EavropId eavropId, ReceivedDocumentDTO doc){
		User currentUser = this.currentUserService.getCurrentUser();
		AddReceivedInternalDocumentCommand command = 
				new AddReceivedInternalDocumentCommand(eavropId, doc.getName(), getHsaId(currentUser), 
						currentUser.getFullName(), currentUser.getActiveRole().name(), getUserOrganisation(currentUser),getUserUnit(currentUser));
		return command;
	}

	@Override
	public void addRequestedDocuments(EavropId eavropId, RequestedDocumentDTO doc) {
		RequestedDocument requestedDocument = this.eavropDocumentService.addRequestedDocument(createAddRequestedDocumentCommand(eavropId, doc));
		this.fmuEventService.publishDocumentRequestedEvent(eavropId, requestedDocument);
		
	}

	@Transactional(readOnly=true)
	private AddRequestedDocumentCommand createAddRequestedDocumentCommand(EavropId eavropId, RequestedDocumentDTO doc){
		User currentUser = this.currentUserService.getCurrentUser();
		AddRequestedDocumentCommand command = 
				new AddRequestedDocumentCommand(eavropId, doc.getName(), getHsaId(currentUser), 
						currentUser.getFullName(), currentUser.getActiveRole().name(), getUserOrganisation(currentUser),getUserUnit(currentUser), doc.getComment());
		return command;
	}

	
	@Override
	public void addBooking(BookingRequestDTO changeRequestDto) {
		BookingType bookingType = changeRequestDto.getBookingType();
		Long bookingDateMilis = changeRequestDto.getBookingDate();
		TimeDTO startTime = changeRequestDto.getBookingStartTime();
		TimeDTO endTime = changeRequestDto.getBookingEndTime();
		DateTime startDateTime = new DateTime(bookingDateMilis).withTime(startTime.getHour(),startTime.getMinute(), 0, 0);
		DateTime endDateTime = new DateTime(bookingDateMilis).withTime(endTime.getHour(),	endTime.getMinute(), 0, 0);
		EavropId eavropId = new EavropId(changeRequestDto.getEavropId());
		
		CreateBookingCommand command = new CreateBookingCommand(eavropId, bookingType, startDateTime, endDateTime, 
				changeRequestDto.getPersonName(), changeRequestDto.getPersonRole(),
				changeRequestDto.getAdditionalService(), changeRequestDto.getUseInterpreter());

		BookingId bookingId = this.eavropBookingService.createBooking(command);
		
		this.fmuEventService.publishBookingCreatedEvent(eavropId, bookingId);
		
	}

	@Override
	public void modifyBooking(BookingModificationRequestDTO changeRequestData) {
		ChangeBookingStatusCommand changeBookingStatusCommand = createChangeBookingStatusCommand(changeRequestData);
		this.eavropBookingService.changeBookingStatus(changeBookingStatusCommand);
		
		if(changeBookingStatusCommand.getBookingStatus().isDeviant()){
			this.fmuEventService.publishBookingDeviationEvent(changeBookingStatusCommand.getEavropId(), changeBookingStatusCommand.getBookingId());
		}
	}
	
	private ChangeBookingStatusCommand createChangeBookingStatusCommand(BookingModificationRequestDTO changeRequestData){
		User currentUser = this.currentUserService.getCurrentUser();
		ChangeBookingStatusCommand command = new ChangeBookingStatusCommand(new EavropId(
				changeRequestData.getEavropId()), new BookingId(changeRequestData.getBookingId()),
				changeRequestData.getBookingStatus(), changeRequestData.getComment(),
				getHsaId(currentUser), currentUser.getFullName(), currentUser.getActiveRole().name(),
				getUserOrganisation(currentUser), getUserUnit(currentUser));
		
		return command;
	}

	@Override
	public void modifyTolkBooking(TolkBookingModificationRequestDTO changeRequestData) {
		ChangeInterpreterBookingStatusCommand changeInterpreterBookingStatusCommand = createChangeInterpreterBookingStatusCommand(changeRequestData);
		this.eavropBookingService.changeInterpreterBookingStatus(changeInterpreterBookingStatusCommand);
		
		if(changeInterpreterBookingStatusCommand.getInterpreterbookingStatus().isDeviant()){
			this.fmuEventService.publishInterpreterBookingDeviationEvent(changeInterpreterBookingStatusCommand.getEavropId(), changeInterpreterBookingStatusCommand.getBookingId());
		}
	}
	
	private ChangeInterpreterBookingStatusCommand createChangeInterpreterBookingStatusCommand(TolkBookingModificationRequestDTO changeRequestData){
		User currentUser = this.currentUserService.getCurrentUser();
		ChangeInterpreterBookingStatusCommand command = new ChangeInterpreterBookingStatusCommand(
				new EavropId(changeRequestData.getEavropId()), 
				new BookingId(changeRequestData.getBookingId()), 
				changeRequestData.getBookingStatus(),
				changeRequestData.getComment(), 
				getHsaId(currentUser), 
				currentUser.getFullName(),
				currentUser.getActiveRole().name(), 
				getUserOrganisation(currentUser),
				getUserUnit(currentUser));
		return command;
	}

	@Override
	public void addNote(AddNoteRequestDTO addRequest) {
		User currentUser = this.currentUserService.getCurrentUser();
		AddNoteCommand command = new AddNoteCommand(new EavropId(addRequest.getEavropId()),
				addRequest.getText(), getHsaId(currentUser), currentUser.getFullName(), currentUser
						.getActiveRole().name().toLowerCase(), getUserOrganisation(currentUser),
				getUserUnit(currentUser));
		this.eavropNoteService.addNote(command);
	}

	@Override
	public void removeNote(String eavropId, String noteId) {
		User currentUser = this.currentUserService.getCurrentUser();
		RemoveNoteCommand command = new RemoveNoteCommand(new EavropId(eavropId),
				new NoteId(noteId), new HsaId(currentUser.getHsaId()));
		this.eavropNoteService.removeNote(command);
	}
	
	@Override
	@Transactional(readOnly=true)
	public PatientDTO getPatientInfo(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		PatientDTOMapper patientMapper = new PatientDTOMapper();
		return  patientMapper.map(eavropForUser, currentUserService.getCurrentUser().getActiveRole() == Role.ROLE_UTREDARE);
	}

	@Override
	@Transactional(readOnly=true)
	public EavropDTO getEavrop(EavropId eavropId) {
		EavropDTOMapper eavropMapper = new EavropDTOMapper();
		Eavrop eavropForUser = getEavropForUser(eavropId);
		return eavropMapper.map(eavropForUser, new EavropDTO());
	}

	@Override
	@Transactional(readOnly=true)
	public List<VardgivarenhetDTO> getVardgivarenheter(EavropId eavropId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		Landsting landsting = eavropForUser.getLandsting();
		VardgivarenhetDTOMapper vardgivarenhetMapper = new VardgivarenhetDTOMapper();
		List<VardgivarenhetDTO> vardgivarenhetDTOs = new ArrayList<>();

		for (Vardgivarenhet vardgivarenhet : landsting.getVardgivarenheter()) {
			vardgivarenhetDTOs.add(vardgivarenhetMapper.map(vardgivarenhet));
		}

		return vardgivarenhetDTOs;
	}

	@Override
	public void assignVardgivarenhetToEavrop(EavropId eavropId, Long vardgivarenhetId) {
		Eavrop eavropForUser = getEavropForUser(eavropId);
		Vardgivarenhet vardgivarenhet = fmuListService.findVardgivarenhetById(vardgivarenhetId);
		User currentUser = currentUserService.getCurrentUser();
		
		HsaId vardgivarenhetHsaId = vardgivarenhet.getHsaId();
		HsaId personHsaId = new HsaId(currentUser.getHsaId());
		String personName = currentUser.getFullName();
		String personRole = currentUser.getActiveRole().toString();
		String personOrganisation = vardgivarenhet.getVardgivare().getName();
		String personUnit = currentUser.getUnit();
		
		AssignEavropCommand cmd = new AssignEavropCommand(eavropForUser.getEavropId(),
				vardgivarenhetHsaId, personHsaId, personName, personRole, personOrganisation,
				personUnit);
		this.eavropAssignmentService.assignEavropToVardgivarenhet(cmd);
		
		this.fmuEventService.publishEavropAssignedToVardgivarenhetEvent(eavropForUser.getEavropId(), vardgivarenhetHsaId);
	}
	
	@Override
	public void acceptEavropAssignment(EavropId eavropId) {
		User currentUser = currentUserService.getCurrentUser();
		Vardgivarenhet vardgivarenhet = getVardgivarenhetFromUser();

		HsaId vardgivarenhetHsaId = vardgivarenhet.getHsaId();
		HsaId personHsaId = new HsaId(currentUser.getHsaId());
		String personName = currentUser.getFullName();
		String personRole = currentUser.getActiveRole().toString();
		String personOrganisation = vardgivarenhet.getVardgivare().getName();
		String personUnit = currentUser.getUnit();
		AcceptEavropAssignmentCommand assignCommand = new AcceptEavropAssignmentCommand(eavropId,
				vardgivarenhetHsaId, personHsaId, personName, personRole, personOrganisation,
				personUnit);
		this.eavropAssignmentService.acceptEavropAssignment(assignCommand);
		this.fmuEventService.publishEavropAcceptedByVardgivarenhetEvent(eavropId, vardgivarenhetHsaId);

	}

	@Override
	public void rejectEavropAssignment(EavropId eavropId) {
		User currentUser = currentUserService.getCurrentUser();
		Vardgivarenhet vardgivarenhet = getVardgivarenhetFromUser();

		HsaId vardgivarenhetHsaId = vardgivarenhet.getHsaId();
		HsaId personHsaId = new HsaId(currentUser.getHsaId());
		String personName = currentUser.getFullName();
		String personRole = currentUser.getActiveRole().toString();
		String personOrganisation = vardgivarenhet.getVardgivare().getName();
		String personUnit = currentUser.getUnit();
		RejectEavropAssignmentCommand rejectCommand = new RejectEavropAssignmentCommand(eavropId,
				vardgivarenhetHsaId, personHsaId, personName, personRole, personOrganisation,
				personUnit, "");
		this.eavropAssignmentService.rejectEavropAssignment(rejectCommand);
		this.fmuEventService.publishEavropRejectedByVardgivarenhetEvent(eavropId, vardgivarenhetHsaId);
	}

	@Override
	@Transactional
	public CompensationDTO getCompensations(EavropId eavropId) {
		Eavrop eavrop = this.fmuListService.findByEavropId(eavropId);
		CompensationDTOMapper compensationMapper = new CompensationDTOMapper();

		return compensationMapper.map(eavrop);
	}
	
	
	@Override
	public Eavrop getEavropForUser(EavropId eavropId) {
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop eavrop = null;

		if (currentUser.getActiveRole() == Role.ROLE_SAMORDNARE) {
			eavrop = this.fmuListService.findByEavropIdAndLandstingCode(eavropId, new LandstingCode(currentUser.getLandstingCode()));
		} else if (currentUser.getActiveRole() == Role.ROLE_UTREDARE) {
			eavrop = this.fmuListService.findByEavropIdAndVardgivarenhetHsaId(eavropId, new HsaId(currentUser.getVardenhetHsaId()));
		} else {
			throw new IllegalStateException("User has no active role");
		}

		return eavrop;
	}

	/*
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
			Landsting landsting = this.fmuListService.findLandstingByLandstingCode(new LandstingCode(user.getLandstingCode()));
			retval = landsting.getName();
			break;
		case ROLE_UTREDARE:
			Vardgivarenhet vardgivarenhet = this.fmuListService.findVardgivarenhetByHsaId(new HsaId(user.getVardenhetHsaId()));
			retval = vardgivarenhet.getVardgivare().getName();
			break;
		default:
			break;
		}

		return retval;
	}

	/*
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
			Vardgivarenhet vardgivarenhet = this.fmuListService.findVardgivarenhetByHsaId(new HsaId(user.getVardenhetHsaId()));
			retval = vardgivarenhet.getVardgivare().getName();
			break;
		default:
			break;
		}

		return retval;
	}
	
	/*
	 * Get the HsaId of the user
	 * 
	 * @param currentUser
	 *            The currently logged in user
	 * @return The user's HsaId
	 */
	private HsaId getHsaId(User currentUser) {
		if (!StringUtils.isBlankOrNull(currentUser.getHsaId())) {
			return new HsaId(currentUser.getHsaId());
		}
		return null;
	}
	
	/*
	 * Get the Vardgivarenhet of the user
	 * 
	 * @param currentUser
	 *            The currently logged in user
	 * @return The Vardgivarenhet the user is connected to
	 */
	private Vardgivarenhet getVardgivarenhetFromUser() {
		User currentUser = currentUserService.getCurrentUser();
		return this.fmuListService.findVardgivarenhetByHsaId(new HsaId(currentUser.getVardenhetHsaId()));
	}
}
