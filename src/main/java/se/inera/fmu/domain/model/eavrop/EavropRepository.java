package se.inera.fmu.domain.model.eavrop;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.hos.vardgivare.Vardgivarenhet;
import se.inera.fmu.domain.model.landsting.Landsting;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface EavropRepository extends JpaRepository<Eavrop, Long> {

	Eavrop findByEavropId(EavropId eavropId);

	Eavrop findByArendeId(ArendeId arendeId);

	Eavrop findByEavropIdAndLandsting(EavropId eavropId, Landsting landsting);
	
	@Query("SELECT e FROM Eavrop e "
		+ " WHERE e.eavropId = :eavropId "
		+ " AND e.currentAssignment.vardgivarenhet = :vardgivarenhet ")
	Eavrop findByEavropIdAndVardgivare(@Param("eavropId") EavropId eavropId, 
									   @Param("vardgivarenhet") Vardgivarenhet vardgivarenhet);
	
	 	
	List<Eavrop> findAllByLandsting(Landsting landsting);
	
	List<Eavrop> findByLandstingAndEavropStateIn(Landsting landsting, List<EavropState> eavropStates);
	

	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.createdDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.createdDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByLandstingAndCreateDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);


	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.startDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.startDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByLandstingAndStartDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") LocalDate fromDate,
    		@Param("toDate") LocalDate toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);
	
	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.intygSignedDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.intygSignedDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByLandstingAndIntygSignedDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);

	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.createdDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.createdDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByVardgivarenhetAndCreateDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);
	
	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.startDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.startDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByVardgivarenhetAndStartDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") LocalDate fromDate,
    		@Param("toDate") LocalDate toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);

	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.intygSignedDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.intygSignedDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public Page<Eavrop> findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates,
    		Pageable pageable);
}
