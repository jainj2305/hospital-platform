package com.hospital.patient_service.repository;

import com.hospital.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByIdAndActiveTrue(UUID id);

    List<Patient> findByActiveTrue();
}
