# BNR Licensing Portal - Backend README

## Overview

This backend provides the REST API for the BNR Licensing & Compliance Portal. It is built with Spring Boot, PostgreSQL, Flyway, JWT authentication, RBAC authorization, document metadata management, workflow enforcement, and immutable audit logging.

## Technology Stack

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Flyway
- BCrypt
- Scalar/OpenAPI

## Local Backend URL

```text
http://localhost:8080
```

API base URL:

```text
http://localhost:8080/api
```

Scalar API documentation:

```text
http://localhost:8080/scalar
```

## Prerequisites

Install:

- Java 21
- Maven or use included Maven wrapper
- PostgreSQL

## Database Setup

Create database:

```sql
CREATE DATABASE bnr_exam;

If your PostgreSQL uses the default postgres user, set:

username: postgres
password: your_password

If you installed PostgreSQL using Homebrew on macOS, your username may be your macOS username.
Update application.yml accordingly.
```

Update `src/main/resources/application.yml`: ddl-auto: validate to ddl-auto: none

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bnr_exam
    username: db_user
    password: your_password

  jpa:
    hibernate:
      ddl-auto: none

  flyway:
    enabled: true

  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 5MB

springdoc:
  api-docs:
    path: /api-docs

scalar:
  url: /api-docs

jwt:
  secret: 1234567890123456789012345678901234567890123456789012345678901234
  expiration: 86400000

app:
  storage:
    root: uploads

server:
  port: 8080
```

## Run Backend

From the backend folder: cd backend

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw spring-boot:run
```

## Authentication Flow

Login endpoint:

```http
POST /api/auth/login
```

Example:

```json
{
  "email": "admin@bnr.rw",
  "password": "admin123"
}
```

Response:

```json
{
  "token": "jwt-token",
  "role": "ADMIN",
  "names": "System Admin",
  "permission": ["MANAGE_USERS", "VIEW_ALL"]
}
```

Use the token in protected requests:

```http
Authorization: Bearer <token>
```

## Default Test Users

| Role | Email | Password |
|---|---|---|
| ADMIN | admin@bnr.rw | admin123 |
| APPLICANT | applicant@bnr.rw | applicant123 |
| REVIEWER | reviewer@bnr.rw | reviewer123 |
| APPROVER | approver@bnr.rw | approver123 |

If users do not exist yet, create the first admin using:

```http
POST /api/setup/admin
```

Then login as admin and create other users with:

```http
POST /api/users
```

## Role Permission Mapping (did Flyway migration for mapping)

| Role | Permissions |
|---|---|
| APPLICANT | CREATE_APPLICATION, VIEW_OWN_APPLICATION |
| REVIEWER | REVIEW_APPLICATION, REQUEST_INFORMATION, VIEW_ASSIGNED_APPLICATIONS |
| APPROVER | APPROVE_APPLICATION, REJECT_APPLICATION |
| ADMIN | MANAGE_USERS, VIEW_ALL |

## Main API Endpoints

### Auth

```http
POST /api/auth/login
```

### Users

```http
POST /api/users
```

### Applications

```http
POST /api/applications
GET /api/applications
GET /api/applications/{id}
```

`POST /api/applications` uses `multipart/form-data`:

Text fields:

```text
institutionName
licenseType
businessAddress
```

Files:

```text
MARKET_RISK_MANAGEMENT_POLICY
CLIENT_ASSET_PROTECTION_POLICY
TRADING_POLICY
```

### Review

```http
POST /api/review/{id}/start
POST /api/review/{id}/complete
POST /api/review/{id}/request-info
```

### Approval

```http
POST /api/approval/{id}/approve
POST /api/approval/{id}/reject
```

### Audit

```http
GET /api/applications/{id}/audit-trail
```

### Documents

```http
GET /api/applications/{id}/documents
```

## Business Rules

- SUBMITTED -> UNDER_REVIEW
- UNDER_REVIEW -> REVIEW_COMPLETED
- UNDER_REVIEW -> INFO_REQUESTED
- INFO_REQUESTED -> UNDER_REVIEW
- REVIEW_COMPLETED -> APPROVED or REJECTED
- APPROVED and REJECTED are final.
- Reviewer cannot approve or reject the same application.
- Documents are max 5MB and must be PDF/DOC/DOCX.

## CORS

The backend allows the Angular frontend origin: SecurityConfig

```text
http://localhost:4200
```

## Notes for Reviewers

The backend uses Flyway, so database schema is created automatically when the application starts. Do not manually create tables.
