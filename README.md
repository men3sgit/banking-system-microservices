#  Banking System Microservices

**A modular, event-driven banking platform built with Java, Spring Boot & Kafka**

---

##  Project Description

This project implements a **modern microservices architecture** for a banking system, featuring:

- Domain-specific services like *Auth*, *Profile*, *Account*, *Transaction*, *Notification*, and *OTP*.
- Asynchronous communication via **Kafka** for event-driven workflows.
- Separation of concerns: **Auth** handles registration & OTP logic; **Notification** handles user communication.
- Scalable, secure design with **Spring Boot**, domain-driven services, and centralized configuration.

It's a clean, production-ready template for building scalable microservices with event-driven integration.

---

##  Tech Stack

| Layer                 | Technologies |
|----------------------|--------------|
| Framework             | Spring Boot, Spring Cloud |
| Messaging             | Apache Kafka |
| Configuration         | application.yml + Spring Config |
| Data Storage          | PostgreSQL (or others) |
| Persistence (OTP)     | Redis |
| Concurrency           | ExecutorService |
| Security              | Spring Security (JWT / OAuth2, optional) |
| Build & CI/CD         | Maven / Gradle, Docker, Docker Compose |

---

##  Key Features

- **Decoupled microservices** with clear service boundaries.
- **Event-driven flows**: e.g., user registration triggers `ProfileCreated` → OTP issuance.
- **OTP framework** with policy-based handling (length, TTL, retry limits).
- **Notification service** consumes OTP events and routes via SMS, Email using enums (`DeliveryChannel`).
- Published events are **transaction-committed**, ensuring data consistency.
- High testability and extensibility — new services can easily be added.




### Prerequisites
- Java 17+
- Docker & Docker Compose
- Kafka (or included in compose)
- Redis (or included)
- Maven or Gradle

### Clone & Run Locally
```bash
git clone https://github.com/men3sgit/banking-system-microservices.git
cd banking-system-microservices
docker-compose up -d    # brings up Kafka, Redis, DB, etc.
# In another terminal:
mvn clean install       # or ./gradlew build
