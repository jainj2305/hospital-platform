package com.hospital.notification_service.event;

import java.util.UUID;

public record PaymentFailedEvent(
        UUID billId,
        String reason
) {}

