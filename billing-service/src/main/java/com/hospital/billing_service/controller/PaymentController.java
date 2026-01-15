package com.hospital.billing_service.controller;

import com.hospital.billing_service.dto.PaymentRequest;
import com.hospital.billing_service.entity.Payment;
import com.hospital.billing_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping
    public ResponseEntity<Payment> pay(
            @RequestHeader("Idempotency-Key") String key,
            @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(service.processPayment(request.getBillId(), request.getAmount(), key));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(service.getAllPayments());
    }
}
