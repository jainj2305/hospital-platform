package com.hospital.notification_service.consumer;

import com.hospital.notification_service.event.AppointmentEvent;
import com.hospital.notification_service.event.BillGeneratedEvent;
import com.hospital.notification_service.event.PaymentCompletedEvent;
import com.hospital.notification_service.event.PaymentFailedEvent;
import com.hospital.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private NotificationService service;

    @KafkaListener(topics = "appointment.booked", groupId = "notification-group")
    public void handleAppointmentBooked(AppointmentEvent event) {
        service.sendAppointmentNotification(event);
    }

    @KafkaListener(topics = "bill.generated", groupId = "notification-group")
    public void handleBillGenerated(BillGeneratedEvent event) {
        service.sendBillNotification(event);
    }

    @KafkaListener(topics = "payment.completed", groupId = "notification-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        service.sendPaymentSuccess(event);
    }

    @KafkaListener(topics = "payment.failed", groupId = "notification-group")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        service.sendPaymentFailure(event);
    }
}
