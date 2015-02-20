package se.inera.fmu.domain.model.hos.vardgivare;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Repository class for handling Vardgivare 
 */
public interface VardgivareRepository extends JpaRepository<Vardgivare, Long> {

	Vardgivare findByHsaId(HsaId hsaId);
}
