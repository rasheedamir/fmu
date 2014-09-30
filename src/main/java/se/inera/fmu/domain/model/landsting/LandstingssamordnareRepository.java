package se.inera.fmu.domain.model.landsting;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rickard on 9/9/14.
 */
public interface LandstingssamordnareRepository extends JpaRepository<Landstingssamordnare, Long> {
	
	Landstingssamordnare findByHsaId(HsaId hsaId);
}
