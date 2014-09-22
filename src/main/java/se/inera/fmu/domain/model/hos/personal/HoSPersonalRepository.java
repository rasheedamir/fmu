package se.inera.fmu.domain.model.hos.personal;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Created by Rickard on 8/26/14.
 */
public interface HoSPersonalRepository extends JpaRepository<HoSPersonal, Long> {

	HoSPersonal findByHsaId(HsaId hsaId);
}
