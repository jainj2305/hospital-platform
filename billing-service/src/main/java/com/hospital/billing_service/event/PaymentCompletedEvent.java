package com.hospital.billing_service.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentId,
        UUID billId,
        BigDecimal amount
) {}
