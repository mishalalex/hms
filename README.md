# HMS

HMS stands for Hotel Management System. This project is the backend for an Airbnb-like application, with the current focus on building REST APIs for hotel and room management.

At this stage, the work is backend-only. The goal is to build a solid API and domain foundation first, then extend the system toward booking, payments, guests, users, and the broader workflows needed for a full accommodation platform.

## Overview

The application is a single-module Spring Boot service built around hotel operations. It currently exposes admin-facing APIs for:

- creating, updating, fetching, activating, and deleting hotels
- creating, listing, fetching, and deleting rooms inside hotels
- initializing inventory when hotels or rooms become active
- returning consistent success and error responses through global handlers

## Current Scope

The current implementation is centered on backend API development only.

In scope right now:

- hotel management APIs
- room management APIs
- inventory initialization logic
- persistence with PostgreSQL
- exception handling and response standardization

Planned later:

- booking flows
- guest management
- payment flows
- user and role based features
- broader Airbnb-like business workflows

## Tech Stack

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Lombok
- ModelMapper

## Project Structure

```text
src/main/java/com/mishal/project/HMS/
├── advice/        # global response and exception handling
├── config/        # Spring configuration such as ModelMapper
├── controller/    # REST controllers
├── dto/           # request/response DTOs
├── entity/        # JPA entities and enums
├── exception/     # custom exceptions
├── repository/    # Spring Data JPA repositories
└── service/       # business logic
```

Important runtime configuration lives in `src/main/resources/application.properties`.

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Local Setup

1. Create a PostgreSQL database named `airbnb`.
2. Update `src/main/resources/application.properties` with your local database username and password.
3. Start the application.

### Build and Run

```bash
mvn compile
mvn test
mvn spring-boot:run
```

If you want a quick compile without running tests:

```bash
mvn -DskipTests compile
```

## Configuration

Current default application settings:

- application name: `HMS`
- base API context path: `/api/v1`
- database URL: `jdbc:postgresql://localhost:5432/airbnb`
- Hibernate DDL mode: `update`

Relevant configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/airbnb
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
server.servlet.context-path=/api/v1
```

## API Overview

All endpoints are currently exposed under:

```text
/api/v1
```

### Hotel APIs

- `POST /admin/hotels`
- `GET /admin/hotels`
- `GET /admin/hotels/{hotelId}`
- `PATCH /admin/hotels/{hotelId}`
- `PUT /admin/hotels/{hotelId}`
- `DELETE /admin/hotels/{hotelId}`

### Room APIs

- `POST /admin/hotels/{hotelId}/rooms`
- `GET /admin/hotels/{hotelId}/rooms`
- `GET /admin/hotels/{hotelId}/rooms/{roomId}`
- `DELETE /admin/hotels/{hotelId}/rooms/{roomId}`

## Response Format

Successful and error responses are wrapped in a common response envelope.

Typical response shape:

```json
{
  "data": {},
  "error": null,
  "timeStamp": "2026-04-12T21:00:16.12843"
}
```

Error responses follow the same envelope:

```json
{
  "data": null,
  "error": {
    "message": "Resource not found",
    "status": "404 NOT_FOUND",
    "subErrors": null
  },
  "timeStamp": "2026-04-12T21:00:16.12843"
}
```

## Current Status and Roadmap

The domain model already includes entities such as `Booking`, `Guest`, `Payment`, and `User`, but the currently exposed API surface is focused mainly on hotel and room management.

The next backend milestones are expected to include:

- booking creation and lifecycle management
- inventory usage during booking flows
- guest association with bookings
- payment handling
- richer user roles and authorization

## Notes

- The project currently expects PostgreSQL locally.
- API behavior is evolving as the backend is being built out.
- This repository is intended as the backend foundation for an Airbnb-like system, not the frontend application itself.
