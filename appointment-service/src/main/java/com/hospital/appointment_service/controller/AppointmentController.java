package com.hospital.appointment_service.controller;

import com.hospital.appointment_service.dto.AppointmentRequest;
import com.hospital.appointment_service.entity.Appointment;
import com.hospital.appointment_service.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    public ResponseEntity<Appointment> book(@RequestBody AppointmentRequest appointment) {
        return ResponseEntity.ok(service.bookAppointment(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }
}
