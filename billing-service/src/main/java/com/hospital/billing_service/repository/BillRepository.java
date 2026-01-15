package com.hospital.billing_service.repository;

import com.hospital.billing_service.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    Optional<Bill> findByAppointmentId(UUID appointmentId);
}
