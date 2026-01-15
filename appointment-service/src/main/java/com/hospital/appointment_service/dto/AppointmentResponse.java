package com.hospital.appointment_service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentResponse {

    @NotBlank
    private UUID id;

    @NotBlank
    private UUID patientId;

    @NotBlank
    private UUID doctorId;

    @NotBlank
    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private com.hospital.appointment_service.entity.Appointment.Status status;
}
