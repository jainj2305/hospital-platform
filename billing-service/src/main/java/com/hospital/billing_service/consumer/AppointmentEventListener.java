package com.hospital.billing_service.consumer;

import com.hospital.billing_service.event.AppointmentEvent;
import com.hospital.billing_service.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(AppointmentEventListener.class);
    private final BillingService billingService;

    public AppointmentEventListener(BillingService billingService) {
        this.billingService = billingService;
    }

    @KafkaListener(topics = "appointment.booked", groupId = "billing-group")
    public void consume(AppointmentEvent event) {
        log.info("Received appointment.booked event: {}", event);
        billingService.createBillIfNotExists(event);
    }
}
