package com.hospital.billing_service.exception;

import com.hospital.billing_service.entity.Payment;
import lombok.Getter;

@Getter
public class DuplicatePaymentException extends RuntimeException{
    private final Object details;

    public DuplicatePaymentException(String message, Object payment) {
        super(message);
        this.details = payment;
    }

}
