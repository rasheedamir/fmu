package se.inera.fmu.domain.model.hos.personal;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Work in progress, not finalized. 
 * Should possibly be removed 
 */
public interface HoSPersonalRepository extends JpaRepository<HoSPersonal, Long> {

	HoSPersonal findByHsaId(HsaId hsaId);
}
