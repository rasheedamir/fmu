package se.inera.fmu.domain.model.landsting;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for handling Landsting 
 */
public interface LandstingRepository extends JpaRepository<Landsting, Long> {
	
	Landsting findByLandstingCode(LandstingCode landstingCode);
}
