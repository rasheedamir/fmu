package se.inera.fmu.domain.model.eavrop;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.inera.fmu.domain.model.eavrop.EavropState;
import se.inera.fmu.domain.model.landsting.Landsting;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface EavropRepository extends JpaRepository<Eavrop, Long> {

	Eavrop findByArendeId(ArendeId arendeId);

	List<Eavrop> findAllByLandsting(Landsting landsting);
	
	List<Eavrop> findByLandstingAndEavropStateIn(Landsting landsting, List<EavropState> eavropStates);
	
//	
	
	@Query("SELECT e FROM Eavrop e "
			+ "WHERE e.landsting = :landsting "
			+ " AND e.createdDate >= :fromDate "
			+ " AND e.createdDate < :toDate "
			+ " AND e.eavropState in (:eavropStates) ")
    public List<Eavrop> findByByLandstingAndCreateDateAndEavropStateIn(
    		@Param("landsting") Landsting landsting,
    		@Param("fromDate") DateTime fromDate,
    		@Param("toDate") DateTime toDate,
    		@Param("eavropStates") List<EavropState> eavropStates);
	
	
	
	
//	@Query()
//	List<Eavrop> findByLandstingAndEavropStateTypeIn(Landsting landsting, List<EavropStateType> eavropStateTypes);
}
