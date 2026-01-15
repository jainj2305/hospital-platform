package com.hospital.billing_service.controller;

import com.hospital.billing_service.entity.Bill;
import com.hospital.billing_service.repository.BillRepository;
import com.hospital.billing_service.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/{id}")
    public ResponseEntity<Bill> get(@PathVariable UUID id) {
        return ResponseEntity.ok(billingService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billingService.getAllBills());
    }
}

