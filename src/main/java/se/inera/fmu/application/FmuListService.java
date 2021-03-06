package se.inera.fmu.application;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.Eavrop;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.hos.hsa.HsaId;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;
import se.inera.fmu.domain.model.landsting.LandstingCode;

/**
 * Service for reading entities
 * 
 * @author Rickard on 01/11/14.
 *
 */
public interface FmuListService {

	/**
	 * Finds a Landsting entity by its LandstingCode  
	 * 
	 * * @param landsting
	 * */
	 public Landsting findLandstingByLandstingCode(LandstingCode landstingCode);

	 
	/**
	 * Finds a Vardgivarenhet entity by its HsaId  
	 * 
	 * * @param hsaId
	 * */
	 public Vardgivarenhet findVardgivarenhetByHsaId(HsaId hsaId);

	/**
	 * Finds a Vardgivarenhet entity by its Id  
	 * 
	 * * @param id
	 * */
	 public Vardgivarenhet findVardgivarenhetById(long id);
	 
	/**
	 * Finds a Eavrop entity by its ArendeId  
	 * 
	 * * @param arendeId
	 * */
    public Eavrop findByArendeId(ArendeId arendeId);


	/**
	 * Finds a Eavrop entity by its EavropId  
	 * 
	 * * @param arendeId
	 * */
    public Eavrop findByEavropId(EavropId eavropId);
    
	
	/**
	 * Finds a Eavrop entity by its EavropId  and LandstingCode 
	 * 
	 * @param eavropId
	 * @param landstingCode
	 * */
    Eavrop findByEavropIdAndLandstingCode(EavropId eavropId, LandstingCode landstingCode);

	/**
	 * Finds a Eavrop entity by its EavropId  and HsaId of Vardgivarenhet 
	 * 
	 * @param eavropId
	 * @param hsaId, HsaId of vardgivarenhet
	 * */
    Eavrop findByEavropIdAndVardgivarenhetHsaId(EavropId eavropId, HsaId hsaId);

    /**
	 * Finds a Eavrop entity by its ArendeId  
	 * 
	 * * @param arendeId
	 * */
    public Eavrop findByArendeIdInitialized(ArendeId arendeId);

    
    /**
	 *  Finds all, by a care giver, not accepted Eavrop connected to a Landsting 
	 *  i.e. Eavrop that are in state UNASSIGNED and ASSIGNED and that have been created/ordered in the specified timespan
	 *  Retuns all if fromdate and todate are not specified 
	 *  
	 * @param landsting
	 * @return
	 */
    public Page<Eavrop> findAllNotAcceptedEavropByLandstingAndDateTimeOrdered(Landsting landsting, DateTime fromDate, DateTime toDate, Pageable pageable);

    
    /**
	 *  Finds all, by a care giver, not yet accepted Eavrop assigned to a vardgivarenhet 
	 *  i.e. Eavrop that are in ASSIGNED state and that have been created/ordered in the specified timespan
	 *  Retuns all if fromdate and todate are not specified 
	 *  
	 * @param landsting
	 * @return
	 */
    public Page<Eavrop> findAllNotAcceptedEavropByVardgivarenhetAndDateTimeOrdered(Vardgivarenhet vardgivarenhet, DateTime fromDate, DateTime toDate, Pageable pageable);


    /**
	 *  Finds all 'Ongoing' Eavrop connected to a Landsting 
	 *  i.e. Eavrop that are in ACCEPTED or ON_HOLD state and that have been started in the specified datespan
	 *  Retuns all if fromdate and todate are not specified 
	 *  
	 * @param landsting
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
    public Page<Eavrop> findAllOngoingEavropByLandstingAndDateTimeStarted(Landsting landsting, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    
    /**
	 *  Finds all 'Ongoing' Eavrop accepted by a vardgivarenhet 
	 *  i.e. Eavrop that are in ACCEPTED or ON_HOLD state and that have been started in the specified datespan
	 *  Retuns all if fromdate and todate are not specified 
	 *  
	 * @param vardgivarenhet
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
    public Page<Eavrop> findAllOngoingEavropByVardgivarenhetAndDateTimeStarted(Vardgivarenhet vardgivarenhet, LocalDate fromDate, LocalDate toDate, Pageable pageable);
 
    
    /**
   	 *  Finds all 'Completed' Eavrop connected to a Landsting 
   	 *  i.e. Eavrop that are in SENT, APPROVED or CLOSED state and that have had intyg signed/sent in the specified timespan
   	 *  Retuns all if fromdate and todate are not specified 
   	 *  
   	 * @param landsting
   	 * @param fromDate
   	 * @param toDate
   	 * @return
   	 */
    public Page<Eavrop> findAllCompletedEavropByLandstingAndDateTimeSent(Landsting landsting, DateTime fromDate, DateTime toDate, Pageable pageable);

    /**
	 *  Finds all 'Completed' Eavrop accepted by a vardgivarenhet 
	 *  i.e. Eavrop that are in SENT, APPROVED or CLOSED state and that have had intyg signed/sent in the specified timespan
	 *  Retuns all if fromdate and todate are not specified 
	 *  
	 * @param vardgivarenhet
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
    public Page<Eavrop> findAllCompletedEavropByVardgivarenhetAndDateTimeSent(Vardgivarenhet vardgivarenhet, DateTime fromDate, DateTime toDate, Pageable pageable);
    
}
