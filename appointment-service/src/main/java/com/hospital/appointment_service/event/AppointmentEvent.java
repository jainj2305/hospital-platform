package com.hospital.appointment_service.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentEvent(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        String appointmentTime) {}
