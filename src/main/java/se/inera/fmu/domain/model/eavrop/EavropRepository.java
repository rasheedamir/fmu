package se.inera.fmu.domain.model.eavrop;

import java.util.List;

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
	

	List<Eavrop> findByLandstingAndEavropStateIn(Landsting landsting, List<EavropState> eavropState);
//	
//	@Query()
//	List<Eavrop> findByLandstingAndEavropStateTypeIn(Landsting landsting, List<EavropStateType> eavropStateTypes);
}
