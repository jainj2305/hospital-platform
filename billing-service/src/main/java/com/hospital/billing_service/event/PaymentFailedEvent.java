package com.hospital.billing_service.event;

import java.util.UUID;

public record PaymentFailedEvent(
        UUID billId,
        String reason
) {}

