package se.inera.fmu.domain.model.systemparameter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the SystemParameter entity.
 */
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {
	

	/**
	 * Finds system parameter for defined key
	 * 
	 * Using native query since the TypedQuery would get inserted in the query cache and then it will fail if the corresponding database row is deleted and then inserted again
	 * 
	 * @param key which represents the system parameter key
	 * @return a system parameter if key exists otherwise null
	 */
	@Query(value = "SELECT * FROM SYSTEM_PARAMETER sp WHERE sp.key = :key", nativeQuery = true)
	 public SystemParameter findByKey(@Param("key") String key);

}
