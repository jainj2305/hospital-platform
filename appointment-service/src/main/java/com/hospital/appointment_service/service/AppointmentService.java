package com.hospital.appointment_service.service;

import com.hospital.appointment_service.dto.AppointmentRequest;
import com.hospital.appointment_service.entity.Appointment;
import com.hospital.appointment_service.event.AppointmentEvent;
import com.hospital.appointment_service.exception.SlotAlreadyBookedException;
import com.hospital.appointment_service.repository.AppointmentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private KafkaTemplate<String, AppointmentEvent> kafkaTemplate;

    public Appointment bookAppointment(AppointmentRequest request) {
        System.out.println(">>> BOOK APPOINTMENT METHOD HIT <<<");
        log.info("bookAppointment() invoked");

        if (repository.existsByDoctorIdAndAppointmentTime(
                request.getDoctorId(), request.getAppointmentTime())) {
            throw new SlotAlreadyBookedException("Doctor already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentTime(request.getAppointmentTime());

        Appointment saved = repository.save(appointment);
        log.info("Appointment saved with id={}", saved.getId());

        System.out.println(">>> ABOUT TO SEND KAFKA EVENT <<<");
        AppointmentEvent event = new AppointmentEvent(
                saved.getId(),
                saved.getPatientId(),
                saved.getDoctorId(),
                saved.getAppointmentTime().toString()
        );

        kafkaTemplate.send("appointment.booked", event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka publish FAILED", ex);
                    } else {
                        System.out.println(">>> KAFKA SEND SUCCESS <<<");
                        log.info("Kafka publish SUCCESS offset={}",
                                result.getRecordMetadata().offset());
                    }
                });

        return saved;
    }

    public Appointment get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }
}
