# Concurrent Seat Booking System

A Spring Boot REST API for managing events, zones, seats, and seat reservations. The project focuses on backend engineering concepts such as transactional booking workflows, PostgreSQL persistence, database-level locking, temporary seat holds, and scheduled cleanup.

**Repository:** https://github.com/ojasmehta28/seat-booking-system

## Overview

The application lets clients manage events and their seating inventory, then create bookings for available seats. A booking initially enters a payment-pending state while selected seats are temporarily locked. The application includes a scheduled process to release expired holds.

This is an actively developed backend project. See [Implementation status](#implementation-status) for the current scope and remaining work.

## Technology Stack

- **Language:** Java
- **Framework:** Spring Boot 3
- **Web/API:** Spring MVC, REST controllers
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** PostgreSQL
- **API documentation:** Swagger UI / OpenAPI (springdoc)
- **Build:** Maven Wrapper
- **Other:** Bean validation, centralized exception handling, SLF4J logging, scheduled tasks

A Redis starter dependency is present for possible future work, but Redis-backed functionality is not currently part of the implemented booking flow.

## Features Implemented

- Event, zone, and seat management APIs.
- Persistent storage using PostgreSQL and Spring Data JPA.
- DTO-based request/response handling and validation.
- Centralized API exception handling with `@RestControllerAdvice`.
- Booking workflow using a payment-pending state before confirmation.
- Transactional booking operations with `@Transactional`.
- Pessimistic write locking on seat rows to coordinate concurrent booking attempts.
- Temporary seat holds with an expiry time.
- Scheduled cleanup for expired seat holds.
- Zone-based seat pricing.
- Booking-window validation.
- Per-user seat limits for an event.
- Payment confirmation workflow.
- Swagger/OpenAPI documentation for exploring the API.

> Payment is represented by an application-level confirmation workflow; this project does not currently integrate a real payment provider.

## Booking Lifecycle

The booking statuses currently include:

- `PAYMENT_PENDING` — booking is awaiting payment confirmation.
- `CONFIRMED` — booking has been confirmed.
- `FAILED` — booking has failed.
- `EXPIRED` — the payment-pending booking has expired.

Seat statuses include:

- `AVAILABLE` — the seat can be selected.
- `LOCKED` — the seat is temporarily held.
- `BOOKED` — the seat has been booked.

At a high level, the flow is:

1. A client requests a booking for selected seats.
2. The service obtains database locks for the requested seat rows and checks the booking rules.
3. Available seats are temporarily locked and a payment-pending booking is created.
4. The client submits a payment-confirmation request.
5. On confirmation, the booking is marked confirmed and its seats are marked booked.
6. A scheduled task checks expired holds and releases seats that are no longer reserved.

The booking and payment lifecycle is still being hardened. In particular, concurrency-focused integration tests and race-condition tests are planned; the high-level flow above should not be interpreted as a claim that every edge case has been production-validated.

## Architecture

The application uses a layered Spring Boot structure:

```text
HTTP Client
    |
    v
Controllers
    |
    v
Services / Booking Engine / Pricing Engine
    |
    v
Spring Data JPA Repositories
    |
    v
PostgreSQL
```

- **Controllers** expose REST endpoints.
- **Services** coordinate business rules and transaction boundaries.
- **Booking Engine / Pricing Engine** support booking decisions and pricing logic.
- **Repositories** access persistent entities and apply database queries/locking.
- **Entities and DTOs** represent persisted domain data and API payloads.
- **Global exception handler** standardizes API error handling.
- **Scheduled task** releases expired seat holds.

### Why database locking?

Two requests can attempt to reserve the same seat at nearly the same time. Pessimistic write locking coordinates access to the seat row at the database level, while the transactional service method groups the related booking changes into a transaction. This is more appropriate for shared database state than relying only on Java-level synchronization, which would not coordinate separate application instances.

## Domain Model

The core entities are:

- `Event` — an event for which seats can be reserved.
- `Zone` — a section or category within an event, used for zone-based pricing.
- `Seat` — an individual seat and its current availability/lock state.
- `Booking` — a reservation and its lifecycle status.
- `BookingSeat` — associates a booking with its reserved seats.

## Prerequisites

Install the following before running the application:

- Java 21 (or a compatible JDK for the project configuration)
- PostgreSQL 18 (or a compatible PostgreSQL version)
- Git

The repository includes the Maven Wrapper, so a separate Maven installation is not required.

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ojasmehta28/seat-booking-system.git
cd seat-booking-system
```

### 2. Create the PostgreSQL database

Create a database named `seatbooking` in PostgreSQL:

```sql
CREATE DATABASE seatbooking;
```

Configure the database username and password for your local PostgreSQL installation. Do not put real credentials in source control.

### 3. Configure environment variables

The application uses environment variables for database configuration. Set values matching your local PostgreSQL setup.

**Windows Command Prompt:**

```cmd
set DB_PASSWORD=your_local_database_password
```

If your `application.properties` also externalizes the database URL or username, set the corresponding `DB_URL` and `DB_USERNAME` variables as configured there. Open a new terminal? Remember that variables set with `set` apply only to that Command Prompt session.

**PowerShell:**

```powershell
$env:DB_PASSWORD = "your_local_database_password"
```

Replace the example value with your own local database password. Never commit passwords or other secrets to Git.

### 4. Run the tests

**Windows:**

```cmd
mvnw.cmd test
```

**macOS/Linux:**

```bash
./mvnw test
```

### 5. Start the application

**Windows:**

```cmd
mvnw.cmd spring-boot:run
```

**macOS/Linux:**

```bash
./mvnw spring-boot:run
```

The default Spring Boot port is `8080`, unless overridden by configuration.

## API Documentation

Once the application is running, open Swagger UI:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Use Swagger UI to inspect the available controllers, review request/response schemas, and try the endpoints against your local database.

## Testing Notes

The project has been run with the Maven test command during development. The existing test suite has included a Spring application-context startup test. That test helps verify that the application can start, but it does **not** by itself prove booking correctness under concurrent requests.

Planned test coverage includes:

- Booking an available seat successfully.
- Rejecting a seat that is already locked or booked.
- Two concurrent requests attempting to reserve the same seat.
- Booking-window and per-user seat-limit validation.
- Expired hold cleanup and booking status consistency.
- Payment confirmation after a hold expires.
- Transaction rollback and error scenarios.

## Implementation Status

### Implemented

- Core Spring Boot application and PostgreSQL integration.
- Event, zone, seat, booking, and booking-seat domain model.
- REST controllers, services, repositories, and DTOs.
- Booking rules, zone pricing, and booking-window checks.
- Transactional booking flow and pessimistic seat locking.
- Temporary seat holds and scheduled expiry cleanup.
- Payment-pending and confirmation workflow.
- Swagger/OpenAPI setup.

### Remaining / Future Improvements

- Add automated integration tests for core booking and payment scenarios.
- Add repeatable concurrency tests to verify that the same seat cannot be successfully booked twice.
- Harden races between payment confirmation and hold expiry.
- Improve payment failure handling and idempotency.
- Add representative API request/response examples to the documentation.
- Consider Redis-based caching or rate limiting only when implemented and measured.
- Add deployment configuration and CI checks.

## Security Notes

- Do not commit database passwords, API keys, tokens, or other secrets.
- Use environment variables or a secrets manager for local and deployed environments.
- Rotate any credential that may have been committed to Git history before making the repository public.

## Author

**Ojas Mehta**

- GitHub: https://github.com/ojasmehta28
- LinkedIn: https://linkedin.com/in/ojas2810

---

This project is being developed as a practical exploration of Java backend engineering, database transactions, and concurrent reservation workflows.
