package com.hospital.appointment_service.clients;

import com.hospital.appointment_service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class PatientServiceClient {

    private final RestClient restClient;

    public PatientServiceClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://patient-service:8081") // patient-service
                .build();
    }

    public void validatePatient(UUID patientId) {
        try {
            restClient.get()
                    .uri("/patients/{id}", patientId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Invalid patientId: " + patientId);
        }
    }
}
