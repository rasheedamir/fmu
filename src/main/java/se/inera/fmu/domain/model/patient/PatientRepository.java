package se.inera.fmu.domain.model.patient;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Rasheed on 7/23/14.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByPersonalNumber(String personalNumber);
}
