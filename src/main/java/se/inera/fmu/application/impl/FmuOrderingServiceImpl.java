package se.inera.fmu.application.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.runtime.parser.node.GetExecutor;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.CurrentUserService;
import se.inera.fmu.application.DomainEventPublisher;
import se.inera.fmu.application.FmuListService;
import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.authentication.Role;
import se.inera.fmu.domain.model.authentication.User;
import se.inera.fmu.domain.model.eavrop.*;
import se.inera.fmu.domain.model.eavrop.assignment.EavropAssignedToVardgivarenhetEvent;
import se.inera.fmu.domain.model.eavrop.document.ReceivedDocument;
import se.inera.fmu.domain.model.eavrop.invanare.Invanare;
import se.inera.fmu.domain.model.eavrop.invanare.InvanareRepository;
import se.inera.fmu.domain.model.eavrop.invanare.PersonalNumber;
import se.inera.fmu.domain.model.eavrop.invanare.medicalexamination.PriorMedicalExamination;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.person.Bestallaradministrator;
import se.inera.fmu.domain.model.person.HoSPerson;
import se.inera.fmu.domain.model.shared.Address;
import se.inera.fmu.domain.model.shared.Gender;
import se.inera.fmu.domain.model.shared.Name;
import se.inera.fmu.domain.model.systemparameter.Configuration;
import se.inera.fmu.interfaces.managing.dtomapper.AllEventsDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.DTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.OrderDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.UtredningDTOMapper;
import se.inera.fmu.interfaces.managing.dtomapper.ReceivedDocumentDTOMapper;
import se.inera.fmu.interfaces.managing.rest.EavropResource.OverviewEavropStates;
import se.inera.fmu.interfaces.managing.rest.dto.AllEventsDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropDTO;
import se.inera.fmu.interfaces.managing.rest.dto.EavropPageDTO;
import se.inera.fmu.interfaces.managing.rest.dto.HandelseDTO;
import se.inera.fmu.interfaces.managing.rest.dto.OrderDTO;
import se.inera.fmu.interfaces.managing.rest.dto.ReceivedDocumentDTO;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

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

    /**
     *
     * @param eavropRepository
     * @param invanareRepository
     * @param asyncEventBus
     */
    @Inject
    public FmuOrderingServiceImpl(final EavropRepository eavropRepository, final InvanareRepository invanareRepository,
    							  final Configuration configuration, final DomainEventPublisher domainEventPublisher,
    							  final LandstingRepository landstingRepository, final CurrentUserService currentUser, 
    							  final VardgivarenhetRepository vardgivarEnhetRepository,
                                  final FmuListService fmuListService) {
        this.eavropRepository = eavropRepository;
        this.invanareRepository = invanareRepository;
        this.configuration = configuration;
        this.domainEventPublisher = domainEventPublisher;
        this.landstingRepository = landstingRepository;
        this.currentUserService = currentUser;
        this.vardgivarEnhetRepository = vardgivarEnhetRepository;
        this.fmuListService = fmuListService;
    }
    
      /**
     * Creates a an eavrop.
     *
     * @param aCommand : CreateEavropCommand
     * @return arendeId
     */
    @Override
    public ArendeId createEavrop(CreateEavropCommand aCommand) {
        
        Invanare invanare = createInvanare(aCommand.getPersonalNumber(), aCommand.getInvanareName(), aCommand.getInvanareGender(),
                aCommand.getInvanareHomeAddress(), aCommand.getInvanareEmail(), aCommand.getInvanareSpecialNeeds());
    	
        Bestallaradministrator bestallaradministrator = createBestallaradministrator(aCommand.getAdministratorName(),
                aCommand.getAdministratorBefattning(), aCommand.getAdministratorOrganisation(), aCommand.getAdministratorEnhet(),
                aCommand.getAdministratorPhone(), aCommand.getAdministratorEmail());
        
        Interpreter interpreter= new Interpreter(aCommand.getInterpreterLanguages());
        
        EavropProperties props = getEavropProperties();
        
        PriorMedicalExamination priorMedicalExamination = createPriorMedicalExamination(aCommand);
        
        Eavrop eavrop = EavropBuilder.eavrop()
		.withArendeId(aCommand.getArendeId())
		.withUtredningType(aCommand.getUtredningType())
		.withInvanare(invanare)
		.withLandsting(aCommand.getLandsting())
		.withBestallaradministrator(bestallaradministrator)
		.withInterpreter(interpreter)
		.withEavropProperties(props)
		.withDescription(aCommand.getDescription())
		.withUtredningFocus(aCommand.getUtredningFocus())
		.withAdditionalInformation(aCommand.getAdditionalInformation())
		.withPriorMedicalExamination(priorMedicalExamination)
		.build();
        
        eavrop = eavropRepository.save(eavrop);
        
        log.debug(String.format("invanare created :: %s", invanare));
        log.debug(String.format("bestallaradministrator created :: %s", bestallaradministrator));
        log.debug(String.format("eavrop created :: %s", eavrop));

        //Publish an event to notify the interested listeners/subscribers that an eavrop has been created.
        
        domainEventPublisher.post(new EavropCreatedEvent(eavrop.getEavropId()));

        return eavrop.getArendeId();
    }

	/**
     *
     * @param personalNumber
     * @param invanareName
     * @param invanareGender
     * @param invanareHomeAddress
     * @param invanareEmail
     * @param specialNeeds
     * @return
     */
    private Invanare createInvanare(PersonalNumber personalNumber, Name invanareName, Gender invanareGender, Address invanareHomeAddress,
                                    String invanareEmail, String specialNeeds ){
    	Invanare invanare = new Invanare(personalNumber, invanareName, invanareGender, invanareHomeAddress, invanareEmail, specialNeeds);
    	invanare = invanareRepository.save(invanare);
        return invanare;
    }

    /**
     *
     * @param name
     * @param befattning
     * @param organisation
     * @param phone
     * @param email
     * @return
     */
    private Bestallaradministrator createBestallaradministrator(String name, String befattning, String organisation, String unit, String phone, String email){
    	Bestallaradministrator bestallaradministrator = new Bestallaradministrator(name, befattning, organisation, unit, phone, email);
    	//TODO: Set up repository, for this subclass or abstract superclass; 
//    	bestallaradministrator = bestallaradministrator.save(invanare);
    	return bestallaradministrator;
    }

    private PriorMedicalExamination createPriorMedicalExamination(String examinedAt, String medicalLeaveIssuedAt, String issuerName, String issuerRole, String issuerOrganisation, String issuerUnit ){
    	HoSPerson issuer = new HoSPerson(issuerName, issuerRole, issuerOrganisation, issuerUnit);
    	return new PriorMedicalExamination(examinedAt, medicalLeaveIssuedAt, issuer);
    }

    private PriorMedicalExamination createPriorMedicalExamination(CreateEavropCommand aCommand) {
    	HoSPerson issuer = new HoSPerson(aCommand.getPriorMedicalLeaveIssuedByName(), 
    									 aCommand.getPriorMedicalLeaveIssuedByBefattning(),
    									 aCommand.getPriorMedicalLeaveIssuedByOrganisation(),
    									 aCommand.getPriorMedicalLeaveIssuedByEnhet());
    	return new PriorMedicalExamination(aCommand.getPriorExaminedAt(), aCommand.getPriorMedicalLeaveIssuedAt(), issuer);
	}


    
    private EavropProperties getEavropProperties(){
    	int startDateOffset = getConfiguration().getInteger(Configuration.KEY_EAVROP_START_DATE_OFFSET, 3);    	
    	int acceptanceValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_ACCEPTANCE_VALID_LENGTH, 5);
    	int assessmentValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_ASSESSMENT_VALID_LENGTH, 25);
    	int completionValidLength = getConfiguration().getInteger(Configuration.KEY_EAVROP_COMPLETION_VALID_LENGTH, 10);
    	
    	return new EavropProperties(startDateOffset, acceptanceValidLength, assessmentValidLength, completionValidLength);
    }
    
    private Configuration getConfiguration(){
    	return this.configuration;
    }

	@Override
	public EavropPageDTO getOverviewEavrops(long fromDate, long toDate,
			OverviewEavropStates state, Pageable paginationSpecs) {
		User currentUSer = this.currentUserService.getCurrentUser();
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);

		switch (currentUSer.getActiveRole()) {
		case LANDSTINGSSAMORDNARE:
			if (currentUSer.getLandstingCode() == null)
				return null;
			Landsting landsting = this.landstingRepository
					.findByLandstingCode(new LandstingCode(currentUSer
							.getLandstingCode()));

			switch (state) {
			case NOT_ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllNotAcceptedEavropByLandstingAndDateTimeOrdered(
								landsting, startDate, endDate, paginationSpecs));
			case ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllOngoingEavropByLandstingAndDateTimeStarted(
								landsting, startDate.toLocalDate(),
								endDate.toLocalDate(), paginationSpecs));
			case COMPLETED:
				return constructOverviewDTO(this.fmuListService
						.findAllCompletedEavropByLandstingAndDateTimeSigned(
								landsting, startDate, endDate, paginationSpecs));
			default:
				return null;
			}
		case UTREDARE:
			if (currentUSer.getVardenhetHsaId() == null) {
				return null;
			}
			Vardgivarenhet vardgivarenhet = this.fmuListService
					.findVardgivarenhetByHsaId(new HsaId(currentUSer
							.getVardenhetHsaId()));

			switch (state) {
			case NOT_ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered(
								vardgivarenhet, startDate, endDate,
								paginationSpecs));
			case ACCEPTED:
				return constructOverviewDTO(this.fmuListService
						.findAllOngoingEavropByVardgivarenhetAndDateTimeStarted(
								vardgivarenhet, startDate.toLocalDate(),
								endDate.toLocalDate(), paginationSpecs));
			case COMPLETED:
				return constructOverviewDTO(this.fmuListService
						.findAllCompletedEavropByVardgivarenhetAndDateTimeSigned(
								vardgivarenhet, startDate, endDate,
								paginationSpecs));
			default:
				return null;
			}
		default:
			return null;
		}
	}

	private EavropPageDTO constructOverviewDTO(Page<Eavrop> eavrops) {
		List<EavropDTO> data = new ArrayList<EavropDTO>();
		DTOMapper eavropMapper = new DTOMapper();

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
		Eavrop eavrop = this.eavropRepository.findByEavropId(new EavropId(
				eavropId));
		if (eavrop != null)
			return mapper.map(eavrop);

		return null;
	}

	
	protected Eavrop getEavropForUser(EavropId id){
		User currentUser = this.currentUserService.getCurrentUser();
		Eavrop result = null;
		
		if(currentUser.getActiveRole() == Role.LANDSTINGSSAMORDNARE){
			Landsting landsting = this.landstingRepository.findByLandstingCode(new LandstingCode(currentUser.getLandstingCode()));
			result = this.eavropRepository.findByEavropIdAndLandsting(id, landsting);
		} else if(currentUser.getActiveRole() == Role.UTREDARE){
			Vardgivarenhet ve = this.vardgivarEnhetRepository.findByHsaId(new HsaId(currentUser.getVardenhetHsaId()));
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
		
		for(ReceivedDocument doc : eavropForUser.getReceivedDocuments()){
			result.add(mapper.map(doc));
		}
		
		return result;
	}
	
}
