package se.inera.fmu.domain.model.hos.vardgivare;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rasheed on 8/26/14.
 */
public interface VardgivarenhetRepository extends JpaRepository<Vardgivarenhet, Long> {

	Vardgivarenhet findByHsaId(HsaId hsaId);
	
	Set<Vardgivarenhet> findByVardgivare(Vardgivare vardgivare);
}
