package se.inera.fmu.domain.model.authentication;

import se.inera.fmu.domain.model.authentication.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
