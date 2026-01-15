package com.hospital.patient_service.service;

import com.hospital.patient_service.dto.CreatePatientRequest;
import com.hospital.patient_service.dto.PatientResponse;
import com.hospital.patient_service.entity.Patient;
import com.hospital.patient_service.exception.ResourceNotFoundException;
import com.hospital.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    public PatientResponse create(CreatePatientRequest request) {
        Patient patient = Patient.builder()
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .phone(request.getPhone())
                .build();

        Patient saved = repository.save(patient);

        return mapToResponse(saved);
    }

    public PatientResponse getById(UUID id) {
        Patient patient = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return mapToResponse(patient);
    }

    public List<PatientResponse> getAllPatients() {
        return repository.findByActiveTrue().stream()
                .map(this::mapToResponse).toList();
    }

    public void softDelete(UUID id) {
        Patient patient = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setActive(false);
        repository.save(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phone(patient.getPhone())
                .build();
    }
}
