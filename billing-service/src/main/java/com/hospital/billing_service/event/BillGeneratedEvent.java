package com.hospital.billing_service.event;

import java.math.BigDecimal;
import java.util.UUID;

public record BillGeneratedEvent(
        UUID billId,
        UUID appointmentId,
        BigDecimal totalAmount
) {}
