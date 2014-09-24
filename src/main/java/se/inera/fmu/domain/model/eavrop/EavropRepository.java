package se.inera.fmu.domain.model.eavrop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import se.inera.fmu.domain.model.landsting.Landsting;

/**
 * Created by Rasheed on 7/7/14.
 */
public interface EavropRepository extends JpaRepository<Eavrop, Long> {

    Eavrop findByArendeId(ArendeId arendeId);
    
    List<Eavrop> findAllByLandsting(Landsting landsting);
    
}

