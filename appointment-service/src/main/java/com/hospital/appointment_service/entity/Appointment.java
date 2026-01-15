package com.hospital.appointment_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "appointments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"doctorId", "appointmentTime"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false)
    private UUID doctorId;

    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    public void onCreate() {
        if (status == null) {
            status = Status.BOOKED;
        }
    }

    public enum Status {
        BOOKED,
        CHECKED_IN,
        CANCELLED,
        COMPLETED
    }

}
