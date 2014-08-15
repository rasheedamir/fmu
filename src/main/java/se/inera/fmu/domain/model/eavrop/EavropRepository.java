package se.inera.fmu.domain.model.eavrop;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface EavropRepository extends JpaRepository<Eavrop, Long> {

    Eavrop findByÄrendeId(ÄrendeId ärendeId);
}
