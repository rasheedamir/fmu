package se.inera.fmu.domain.model.eavrop;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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

	Eavrop findByArendeId(ArendeId arendeId);

	List<Eavrop> findAllByLandsting(Landsting landsting);
	
	List<Eavrop> findByLandstingAndEavropStateIn(Landsting landsting, List<EavropState> eavropStates);
	

	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.createdDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.createdDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByLandstingAndCreateDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);


	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.startDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.startDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByLandstingAndStartDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") LocalDate fromDate,
    		@Param("toDate") LocalDate toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);

	
	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND (e.intygSignedDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.intygSignedDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByLandstingAndIntygSignedDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);


	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.createdDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.createdDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByVardgivarenhetAndCreateDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);

	
	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.startDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.startDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByVardgivarenhetAndStartDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") LocalDate fromDate,
    		@Param("toDate") LocalDate toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);


	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.currentAssignment.vardgivarenhet = :vardgivarenhet "
			+ " AND (e.intygSignedDate >= :fromDate or :fromDate IS NULL) "
			+ " AND (e.intygSignedDate < :toDate or :toDate IS NULL) "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByVardgivarenhetAndIntygSignedDateAndEavropStateIn(
    		@Param("vardgivarenhet") Vardgivarenhet vardgivarenhet,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);
	
	
	
	
//	@Query()
//	List<Eavrop> findByLandstingAndEavropStateTypeIn(Landsting landsting, List<EavropStateType> eavropStateTypes);
}
