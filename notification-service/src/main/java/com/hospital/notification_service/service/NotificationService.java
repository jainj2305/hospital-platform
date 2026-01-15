package com.hospital.notification_service.service;

import com.hospital.notification_service.event.AppointmentEvent;
import com.hospital.notification_service.event.BillGeneratedEvent;
import com.hospital.notification_service.event.PaymentCompletedEvent;
import com.hospital.notification_service.event.PaymentFailedEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendBillNotification(BillGeneratedEvent event) {
        System.out.println("Bill generated for appointment "
                + event.appointmentId()
                + ", amount: " + event.totalAmount());
    }

    public void sendPaymentSuccess(PaymentCompletedEvent event) {
        System.out.println("Payment successful for bill "
                + event.billId()
                + ", amount: " + event.amount());
    }

    public void sendPaymentFailure(PaymentFailedEvent event) {
        System.out.println("Payment failed for bill "
                + event.billId()
                + ", reason: " + event.reason());
    }

    public void sendAppointmentNotification(AppointmentEvent event) {
        System.out.println("Appointment booked for "
                + event.patientId()
                + " appointmentId: " + event.appointmentId()
                + ", time: " + event.appointmentTime());
    }
}
