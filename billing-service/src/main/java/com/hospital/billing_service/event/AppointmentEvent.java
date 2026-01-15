package com.hospital.billing_service.event;

import java.util.UUID;

public record AppointmentEvent(
        UUID appointmentId,
        UUID patientId,
        UUID doctorId,
        String appointmentTime) {}

