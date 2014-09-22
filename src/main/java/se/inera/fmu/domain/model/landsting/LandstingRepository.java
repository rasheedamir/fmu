package se.inera.fmu.domain.model.landsting;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rickard on 9/9/14.
 */
public interface LandstingRepository extends JpaRepository<Landsting, Long> {
	
	Landsting findByLandstingId(LandstingId landstingId);
}
