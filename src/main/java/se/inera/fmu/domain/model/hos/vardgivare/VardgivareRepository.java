package se.inera.fmu.domain.model.hos.vardgivare;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rickard on 9/9/14.
 */
public interface VardgivareRepository extends JpaRepository<Vardgivare, Long> {

	Vardgivare findByHsaId(HsaId hsaId);
}
