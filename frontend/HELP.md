# BNR Licensing Portal - Frontend README

## Overview

This Angular frontend provides the user interface for the BNR Licensing & Compliance Portal. It supports login, role-based dashboards, application submission, document upload, review workflow, approval workflow, audit trail viewing, and document metadata viewing.

## Technology Stack

- Angular
- Angular Material
- TypeScript
- RxJS
- JWT Bearer Authentication
- HTTP Interceptor
- Route Guards

## Local URLs

Frontend:

```text
http://localhost:4200
```

Backend API:

```text
http://localhost:8080/api
```

## Prerequisites

Install:

- Node.js
- npm
- Angular CLI

## Install Dependencies

From the frontend folder:

```bash
npm install
```

## Environment Configuration

Set the backend API URL in the Angular environment file:

```ts
export const environment = {
  apiUrl: 'http://localhost:8080/api'
};
```

## Run Frontend

```bash
ng serve
```

Open:

```text
http://localhost:4200
```

## Authentication

Login calls:

```http
POST http://localhost:8080/api/auth/login
```

Expected response:

```json
{
  "token": "jwt-token",
  "role": "ADMIN",
  "names": "System Admin",
  "permission": ["MANAGE_USERS", "VIEW_ALL"]
}
```

The frontend should store:

```ts
localStorage.setItem('token', response.token);
localStorage.setItem('user', JSON.stringify(response));
```

The HTTP interceptor attaches:

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

## Frontend Test Flow

1. Login as applicant.
2. Create a new application.
3. Upload all required documents.
4. Confirm status is SUBMITTED.
5. Logout and login as reviewer.
6. Start review.
7. Complete review.
8. Logout and login as approver.
9. Approve or reject.
10. Open audit trail.
11. Open documents list.

## Multipart Upload Integration

Use FormData:

```ts
const formData = new FormData();
formData.append('institutionName', institutionName);
formData.append('licenseType', licenseType);
formData.append('businessAddress', businessAddress);
formData.append('MARKET_RISK_MANAGEMENT_POLICY', marketRiskFile);
formData.append('CLIENT_ASSET_PROTECTION_POLICY', clientAssetFile);
formData.append('TRADING_POLICY', tradingPolicyFile);

this.http.post(`${environment.apiUrl}/applications`, formData);
```

Do not manually set `Content-Type`; the browser sets the multipart automatically.

## Workflow Statuses

- SUBMITTED
- UNDER_REVIEW
- REVIEW_COMPLETED
- INFO_REQUESTED
- APPROVED
- REJECTED

## Common Errors

401: token missing or invalid.
403: authenticated user lacks permission.
400/500: business validation or unexpected server error.

## Notes for Reviewers

The frontend intentionally focuses on clarity, correctness, and role-based workflow actions rather than decorative design.
