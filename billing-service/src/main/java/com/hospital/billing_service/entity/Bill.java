package com.hospital.billing_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "bills",
        uniqueConstraints = @UniqueConstraint(columnNames = "appointmentId")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID appointmentId;

    private BigDecimal totalAmount;
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        PARTIALLY_PAID,
        PAID
    }
}
