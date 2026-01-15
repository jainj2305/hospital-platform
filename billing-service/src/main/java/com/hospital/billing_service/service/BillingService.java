package com.hospital.billing_service.service;

import com.hospital.billing_service.entity.Bill;
import com.hospital.billing_service.event.AppointmentEvent;
import com.hospital.billing_service.event.BillGeneratedEvent;
import com.hospital.billing_service.repository.BillRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {

    private static final Logger log = LoggerFactory.getLogger(BillingService.class);
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void createBillIfNotExists(AppointmentEvent event) {

        if (billRepository.findByAppointmentId(event.appointmentId()).isPresent()) {
            return;
        }

        Bill bill = new Bill();
        bill.setAppointmentId(event.appointmentId());
        bill.setTotalAmount(BigDecimal.valueOf(200));
        bill.setStatus(Bill.Status.PENDING);

        Bill saved = billRepository.save(bill);

        log.info("Publishing bill.generated for appointment {}", saved.getAppointmentId());
        kafkaTemplate.send(
                "bill.generated",
                new BillGeneratedEvent(
                        saved.getId(),
                        saved.getAppointmentId(),
                        saved.getTotalAmount()
                )
        );
    }

    public Bill findById(UUID id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
}

