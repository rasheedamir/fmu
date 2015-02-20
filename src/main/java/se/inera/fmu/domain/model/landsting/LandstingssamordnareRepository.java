package se.inera.fmu.domain.model.landsting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import se.inera.fmu.domain.model.hos.hsa.HsaId;

/**
 * Repository class for handling Landstingssamordnare 
 */
public interface LandstingssamordnareRepository extends JpaRepository<Landstingssamordnare, Long> {
	
	Landstingssamordnare findByHsaId(HsaId hsaId);
	
	@Query("SELECT ls FROM Landstingssamordnare ls "
		+ " JOIN ls.landsting l "
		+ " WHERE l.landstingCode = :landstingCode ")
    public List<Landstingssamordnare> findByLandstingCode(@Param("landstingCode") LandstingCode landstingCode);
	
}
