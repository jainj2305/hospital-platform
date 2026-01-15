package com.hospital.notification_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.notification_service.event.*;
import com.hospital.notification_service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationListener {

    private final NotificationService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationListener(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "appointment.booked", groupId = "notification-group")
    public void handleAppointmentBooked(String message) {
        try {
            AppointmentEvent event =
                    objectMapper.readValue(message, AppointmentEvent.class);
            service.sendAppointmentNotification(event);
        } catch (Exception e) {
            log.error("Failed to process appointment.booked event: {}", message, e);
        }
    }

    @KafkaListener(topics = "bill.generated", groupId = "notification-group")
    public void handleBillGenerated(String message) {
        try {
            BillGeneratedEvent event =
                    objectMapper.readValue(message, BillGeneratedEvent.class);
            service.sendBillNotification(event);
        } catch (Exception e) {
            log.error("Failed to process bill.generated event: {}", message, e);
        }
    }

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void handlePaymentCompleted(String message) {
        try {
            PaymentCompletedEvent event =
                    objectMapper.readValue(message, PaymentCompletedEvent.class);
            service.sendPaymentSuccess(event);
        } catch (Exception e) {
            log.error("Failed to process payment.completed event: {}", message, e);
        }
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-group")
    public void handlePaymentFailed(String message) {
        try {
            PaymentFailedEvent event =
                    objectMapper.readValue(message, PaymentFailedEvent.class);
            service.sendPaymentFailure(event);
        } catch (Exception e) {
            log.error("Failed to process payment.failed event: {}", message, e);
        }
    }
}
