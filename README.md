# BNR Licensing & Compliance Portal

This repository contains the backend and frontend for the BNR Licensing & Compliance Portal technical challenge.

## Project Structure

```text
bnr-licensing-test-project/
  backend/
  frontend/
  docs/
```


## Architecture Summary

- Angular frontend running on http://localhost:4200
- Spring Boot backend running on http://localhost:8080
- PostgreSQL database
- Flyway migrations
- JWT authentication
- RBAC authorization
- Backend-enforced workflow state machine
- Append-only audit trail
- Document upload with versioned metadata


## HELP.MD
```text
Every app both backend and fronted need to run at the same port mentioned
so inside each app contain markdown file called HELP.MD for separate of concerns that acts as guide on how to run for review and also in /docs

cd backend/HELP.MD
cd frontend/HELP.MD
```

## Quick Start

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

Open API docs:

```text
http://localhost:8080/scalar
```

### Frontend

```bash
cd frontend
npm install
ng serve
```

Open:

```text
http://localhost:4200
```

## Create Test Users

First create admin using /api/setup/admin, then create applicant/reviewer/approver from the Users screen or /api/users.

If users do not exist yet, create the first admin using:

```http
POST /api/setup/admin

user Scalar : http://localhost:8080/scalar#tag/setup-controller/POST/api/setup/admin
```

Then login as admin and create other users with:

```http
POST /api/users

```|

## End-to-End Test

1. Login as applicant.
2. Submit application with documents.
3. Login as reviewer.
4. Start review.
5. Complete review.
6. Login as approver.
7. Approve or reject.
8. View audit trail and documents.

Check `docs/` for detailed design document.
