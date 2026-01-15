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
        name = "payments",
        uniqueConstraints = @UniqueConstraint(columnNames = "idempotencyKey")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID billId;
    private BigDecimal amount;

    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        SUCCESS,
        FAILED
    }

    @Override
    public String toString() {
        return "Payment {" +
                "id=" + id +
                ", billId='" + billId + '\'' +
                ", amount='" + amount + '\'' +
                ", key=" + idempotencyKey +
                ", status=" + status +
                '}';
    }
}
