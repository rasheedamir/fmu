package se.inera.fmu.domain.model.invanare;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rasheed on 7/23/14.
 */
public interface InvanareRepository extends JpaRepository<Invanare, Long> {

    Invanare findByPersonalNumber(PersonalNumber personalNumber);
}
