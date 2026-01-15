# Design & Implement a Fault-Tolerant Hospital Billing & Appointment Platform

## 📌 Overview

This project implements a **production-grade, fault-tolerant hospital backend system** using a **microservices architecture**. It manages patient registration, appointment booking, billing & payments, and notifications, while demonstrating **scalability, data consistency, asynchronous processing, and resilience**.


---

## 🧱 Microservices Architecture

### Services Implemented

| Service                   | Responsibility                          | Port |
| ------------------------- | --------------------------------------- | ---- |
| Patient Service           | Patient registration & management       | 8081 |
| Appointment Service       | Appointment booking & lifecycle         | 8082 |
| Billing & Payment Service | Billing generation & payment processing | 8083 |
| Notification Service      | Sends notifications (Kafka consumer)    | 8084 |

Each service:

* Has its **own database schema**
* Is **independently deployable**
* Communicates via **REST (sync)** and **Kafka (async)**

---

## 🗂️ High-Level Architecture Diagram

```
Client
  |
  | REST
  v
Appointment Service  ---> Patient Service
        |
        | Kafka Event (appointment.booked)
        v
Billing Service  ---> Payment Processing
        |
        | Kafka Event (payment.completed / failed)
        v
Notification Service
```

---

## 🔁 Event-Driven Flow (Kafka)

### Kafka Topics

* `appointment.booked`
* `bill.generated`
* `payment.completed`
* `payment.failed`

### End-to-End Flow

1. Appointment is booked
2. `appointment.booked` event published
3. Billing Service generates bill
4. Payment Service processes payment
5. Notification Service sends confirmation

Kafka ensures **loose coupling**, **async processing**, and **event replayability**.

---

## 🔄 Inter-Service Communication

### Patient Validation (Appointment → Patient)

* Appointment Service validates `patientId` via **synchronous REST call**
* Prevents invalid appointments

**Why REST?**

* Simple
* Strong consistency
* Clear service ownership

---

## 💳 Billing & Payment Design

### Billing

* Bill generated on `appointment.booked`

### Payments

* Partial payments supported
* Fully transactional
* Idempotent via `Idempotency-Key`

### Idempotency Strategy

* Unique constraint on `idempotency_key`
* Duplicate requests return existing payment
* Prevents double charging

---

## ❗ Failure Handling Strategy

| Scenario                | Handling               |
| ----------------------- | ---------------------- |
| Duplicate billing       | Bill existence check   |
| Duplicate payment       | Idempotency key        |

Failures are **isolated** and **do not cascade**.

---

## 🐳 Dockerization

### Each Service

* Has its own `Dockerfile`
* Uses pre-built local JAR (no Maven in Docker)

### docker-compose

* Starts:

    * All microservices
    * Kafka
    * Zookeeper
    * PostgreSQL

System starts using:

```bash
docker-compose up --build
```

No manual steps required.

---

## ▶️ Running the Project Locally

### 1. Build Services

```bash
mvn clean package -DskipTests
```

### 2. Start Infrastructure

```bash
docker-compose up
```
---

## ✅ Conclusion

This project demonstrates **real-world microservice design**, **fault tolerance**, and **production readiness**, while remaining **simple enough to explain and run live**.
