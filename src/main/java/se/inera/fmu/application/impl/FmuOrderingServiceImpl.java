package se.inera.fmu.application.impl;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuOrderingService;
import se.inera.fmu.application.impl.command.CreateEavropCommand;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropBuilder;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.eavrop.properties.EavropProperties;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingRepository;
import se.inera.fmu.domain.model.systemparameter.Configuration;

/**
 * 
 * @see FmuOrderingService
 */
@Service
@Validated
@Slf4j
public class FmuOrderingServiceImpl implements FmuOrderingService {

	private final EavropRepository eavropRepository;
	private final Configuration configuration;
	private final LandstingRepository landstingRepository;
	
	private static final int DEFAULT_EAVROP_START_DATE_OFFSET = 3; 
	private static final int DEFAULT_EAVROP_ACCEPTANCE_VALID_LENGTH = 5;
	private static final int DEFAULT_EAVROP_ASSESSMENT_VALID_LENGTH = 25;
	private static final int DEFAULT_EAVROP_COMPLETION_VALID_LENGTH = 10;

	/**
	 *
	 * @param eavropRepository
	 * @param invanareRepository
	 * @param configuration
	 * @param landstingRepository
	 */
	@Inject
	public FmuOrderingServiceImpl(final EavropRepository eavropRepository,
								  final Configuration configuration,
								  final LandstingRepository landstingRepository) {
		this.eavropRepository = eavropRepository;
		this.configuration = configuration;
		this.landstingRepository = landstingRepository;
	}

	/**
	 * Creates a an eavrop.
	 *
	 * @param aCommand  CreateEavropCommand
	 * @return arendeId
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ArendeId createEavrop(CreateEavropCommand aCommand) {
		EavropProperties props = getEavropProperties(aCommand.getUtredningType());

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
		
		return eavrop.getArendeId();
	}

	private EavropProperties getEavropProperties(UtredningType utredningType ) {
		int startDateOffset = getEavropStartDateOffsetProperty(utredningType);
		int acceptanceValidLength = getEavropAcceptanceValidLengthProperty(utredningType);
		int assessmentValidLength = getEavropAssessmentValidLengthProperty(utredningType);
		int completionValidLength = getEavropCompletionValidLengthProperty(utredningType);

		return new EavropProperties(startDateOffset, acceptanceValidLength, assessmentValidLength,
				completionValidLength);
	}
	
	private int getEavropStartDateOffsetProperty(UtredningType utredningType){
		return getConfiguration().getInteger(getKeyByType(Configuration.KEY_EAVROP_START_DATE_OFFSET, utredningType), getConfiguration().getInteger(Configuration.KEY_EAVROP_START_DATE_OFFSET, DEFAULT_EAVROP_START_DATE_OFFSET));
	}

	private int getEavropAcceptanceValidLengthProperty(UtredningType utredningType){
		return getConfiguration().getInteger(getKeyByType(Configuration.KEY_EAVROP_ACCEPTANCE_VALID_LENGTH, utredningType), getConfiguration().getInteger(Configuration.KEY_EAVROP_ACCEPTANCE_VALID_LENGTH, DEFAULT_EAVROP_ACCEPTANCE_VALID_LENGTH));
	}

	private int getEavropAssessmentValidLengthProperty(UtredningType utredningType){
		return getConfiguration().getInteger(getKeyByType(Configuration.KEY_EAVROP_ASSESSMENT_VALID_LENGTH, utredningType), getConfiguration().getInteger(Configuration.KEY_EAVROP_ASSESSMENT_VALID_LENGTH, DEFAULT_EAVROP_ASSESSMENT_VALID_LENGTH));
	}

	private int getEavropCompletionValidLengthProperty(UtredningType utredningType){
		return getConfiguration().getInteger(getKeyByType(Configuration.KEY_EAVROP_COMPLETION_VALID_LENGTH, utredningType), getConfiguration().getInteger(Configuration.KEY_EAVROP_COMPLETION_VALID_LENGTH, DEFAULT_EAVROP_COMPLETION_VALID_LENGTH));
	}

	private String getKeyByType(String key, UtredningType utredningType){
		return key+((utredningType==null)?"":"."+utredningType);
	}
	
	private Configuration getConfiguration() {
		return this.configuration;
	}
}
