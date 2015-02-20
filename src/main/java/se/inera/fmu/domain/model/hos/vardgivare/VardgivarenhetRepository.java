package se.inera.fmu.domain.model.hos.vardgivare;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Repository class for handling Vardgivarenheter 
 */
public interface VardgivarenhetRepository extends JpaRepository<Vardgivarenhet, Long> {

	Vardgivarenhet findByHsaId(HsaId hsaId);
	
	Set<Vardgivarenhet> findByVardgivare(Vardgivare vardgivare);
}
