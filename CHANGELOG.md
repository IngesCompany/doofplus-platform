# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.2.0] - 2026-11-15
### Added
- IAM: sign-up and sign-in with JWT (`/api/v1/authentication/sign-up`, `/api/v1/authentication/sign-in`) and two-factor authentication with a six-digit code; users and roles (`/api/v1/users`, `/api/v1/roles`). Only administrators list the users and read the contact inquiries.
- Lookups by id and the filters that the Web Application uses.
- Subscriptions: change the plan and the billing cycle of a subscription.

### Changed
- Quality & Compliance follows `contratos/quality-api.json`, from the class and database diagrams: 13 resources with people by name and related records by code, `PUT /{id}` to edit, `DELETE /api/v1/evidence/{id}` (409 when it is reviewed), `POST /api/v1/audit-events` and filters by code (`?batchCode=`, `?recordCode=`, `?capaCode=`...) instead of the routes by internal id. Enums are answered in UPPER_SNAKE_CASE and accepted in kebab-case. Batch releases do not change.

## [1.1.0] - 2026-11-14
### Added
- Manufacturing & Batch Management: catalog of products, master formulas, production orders and raw material lots (`/api/v1/products`, `/api/v1/master-formulas`, `/api/v1/production-orders`, `/api/v1/material-lots`), with the batch statuses of the Web Application (start, resume, release and progress).
- IoT Monitoring: ThingsBoard telemetry (`POST /api/v1/telemetry`) and equipment removal (`DELETE /api/v1/equipment/{id}`), rejected with 409 while one of its sensors is monitoring a batch.

### Changed
- Organizations & Profiles: profiles are user profiles (`/api/v1/user-profiles`), as in the Web Application.
- A state change that the current status does not allow answers 409 (`<RESOURCE>_STATE_CONFLICT`); 422 is kept for business rejections (formula not approved, payment rejected).

### Fixed
- Manufacturing: the incidents, events and consumptions of a batch are saved.
- Subscriptions: answer 503 when Niubiz is not available.

## [1.0.0] - 2026-11-13
### Added
- Shared kernel: `Result` and `ApplicationError`, RFC 9457 problem details with messages in English and Spanish, OpenAPI documentation with Swagger UI, CORS configuration, snake case naming strategy, domain event publishing and virtual threads.
- Manufacturing & Batch Management: production batches with their incidents and closing (`/api/v1/production-batches`).
- IoT Monitoring: equipment, sensors, readings and alerts (`/api/v1/equipment`, `/api/v1/sensors`, `/api/v1/readings`, `/api/v1/alerts`).
- Quality & Compliance: quality documents, deviations, evidence, CAPA plans and actions, analytical results, batch releases, audits and their findings, audit events, regulatory reports, collaboration tasks, task comments, traceability gaps and deviation trends.
- Organizations & Profiles: organization registration with RUC validation, facilities, user profiles with their notification preferences, administrative activities and contact inquiries (TS12).
- Subscriptions & Payments: plans and subscriptions with activation, renewal, cancellation and payment through the Niubiz gateway.
- Dockerfile and production profile to deploy the RESTful API with a MySQL database.
