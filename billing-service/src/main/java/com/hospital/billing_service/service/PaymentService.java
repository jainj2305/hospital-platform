package com.hospital.billing_service.service;

import com.hospital.billing_service.entity.Bill;
import com.hospital.billing_service.entity.Payment;
import com.hospital.billing_service.event.PaymentCompletedEvent;
import com.hospital.billing_service.repository.BillRepository;
import com.hospital.billing_service.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Payment processPayment(UUID billId, BigDecimal amount, String idempotencyKey) {

        // Idempotency check
        Optional<Payment> existing =
                paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            return existing.get();
        }

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.getPaidAmount().add(amount)
                .compareTo(bill.getTotalAmount()) > 0) {
            throw new RuntimeException("Overpayment not allowed");
        }

        bill.setPaidAmount(bill.getPaidAmount().add(amount));
        bill.setStatus(
                bill.getPaidAmount().compareTo(bill.getTotalAmount()) == 0
                        ? Bill.Status.PAID
                        : Bill.Status.PARTIALLY_PAID
        );

        billRepository.save(bill);

        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setAmount(amount);
        payment.setIdempotencyKey(idempotencyKey);
        payment.setStatus(Payment.Status.SUCCESS);

        Payment saved = paymentRepository.save(payment);

        kafkaTemplate.send("payment.completed",
                new PaymentCompletedEvent(saved.getId(), billId, amount));

        return saved;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
