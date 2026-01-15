package com.hospital.appointment_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {

    @NotBlank
    private UUID patientId;

    @NotNull
    private UUID doctorId;

    @NotBlank
    private LocalDateTime appointmentTime;
}
