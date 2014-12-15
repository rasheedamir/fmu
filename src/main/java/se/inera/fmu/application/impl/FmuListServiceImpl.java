package se.inera.fmu.application.impl;

import java.util.Arrays;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import se.inera.fmu.application.FmuListService;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropRepository;
import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.hos.vardgivare.VardgivarenhetRepository;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.LandstingRepository;

/**
 * Created by Rickard on 3/11/14.
 *
 * Application Service for listing eavrop in  
 */
@Service
@Validated
@Transactional(readOnly=true)
public class FmuListServiceImpl implements FmuListService {

    private final EavropRepository eavropRepository;

    private final LandstingRepository landstingRepository;
    
    private final VardgivarenhetRepository vardgivarenhetRepository;
    
    /**
     *
     * @param eavropRepository
     * @param landstingRepository
     */
    @Inject
    public FmuListServiceImpl(final EavropRepository eavropRepository, final LandstingRepository landstingRepository, final VardgivarenhetRepository vardgivarenhetRepository) {
        this.eavropRepository = eavropRepository;
        this.landstingRepository = landstingRepository;
        this.vardgivarenhetRepository = vardgivarenhetRepository;
    }
    
    @Override
    public Landsting findLandstingByLandstingCode(LandstingCode landstingCode){
    	Landsting landsting = landstingRepository.findByLandstingCode(landstingCode);
    	
    	Hibernate.initialize(landsting.getVardgivarenheter());
    	
    	return landsting;
    }

    @Override
    public Vardgivarenhet findVardgivarenhetByHsaId(HsaId hsaId) {
    	return this.vardgivarenhetRepository.findByHsaId(hsaId);
    }

    @Override
    public Eavrop findByArendeId(ArendeId arendeId){
    	return this.eavropRepository.findByArendeId(arendeId);
    }
    
    @Override
    public Page<Eavrop> findAllNotAcceptedEavropByLandstingAndDateTimeOrdered(Landsting landsting, DateTime fromDate, DateTime toDate, Pageable pageable){
    	return this.eavropRepository.findByLandstingAndCreateDateAndEavropStateIn(landsting, fromDate, toDate, Arrays.asList(EavropState.NOT_ACCEPTED_STATES), pageable);
    }

	@Override
	public Page<Eavrop> findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered(Vardgivarenhet vardgivarenhet, DateTime fromDate, DateTime toDate, Pageable pageable) {
		return this.eavropRepository.findByVardgivarenhetAndCreateDateAndEavropStateIn(vardgivarenhet, fromDate, toDate,Arrays.asList(EavropState.NOT_ACCEPTED_STATES), pageable);
	}
	
    @Override
    public Page<Eavrop> findAllOngoingEavropByLandstingAndDateTimeStarted(Landsting landsting, LocalDate fromDate, LocalDate toDate, Pageable pageable){
    	return this.eavropRepository.findByLandstingAndStartDateAndEavropStateIn(landsting, fromDate, toDate, Arrays.asList(EavropState.ACCEPTED_STATES), pageable);
    }

	@Override
	public Page<Eavrop> findAllOngoingEavropByVardgivarenhetAndDateTimeStarted(Vardgivarenhet vardgivarenhet, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
		return this.eavropRepository.findByVardgivarenhetAndStartDateAndEavropStateIn(vardgivarenhet, fromDate, toDate,Arrays.asList(EavropState.ACCEPTED_STATES), pageable);
	}

    @Override
    public Page<Eavrop> findAllCompletedEavropByLandstingAndDateTimeSent(Landsting landsting, DateTime fromDate, DateTime toDate, Pageable pageable){
    	return this.eavropRepository.findByLandstingAndIntygSentDateAndEavropStateIn(landsting, fromDate, toDate, Arrays.asList(EavropState.COMPLETED_STATES), pageable);
    }

	@Override
	public Page<Eavrop> findAllCompletedEavropByVardgivarenhetAndDateTimeSent(Vardgivarenhet vardgivarenhet, DateTime fromDate, DateTime toDate, Pageable pageable) {
		return this.eavropRepository.findByVardgivarenhetAndIntygSentDateAndEavropStateIn(vardgivarenhet, fromDate, toDate,Arrays.asList(EavropState.COMPLETED_STATES), pageable);
	}

	@Override
	@Deprecated
	public Eavrop findByArendeIdInitialized(ArendeId arendeId) {
		Eavrop eavrop = findByArendeId(arendeId);
		if(eavrop != null){
			Hibernate.initialize(eavrop.getBookings());
			Hibernate.initialize(eavrop.getBookings().size());
			
		}
		return eavrop;
	}

}
