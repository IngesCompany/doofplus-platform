# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2026-11-13
### Added
- Shared kernel: `Result` and `ApplicationError`, RFC 9457 problem details with messages in English and Spanish, OpenAPI documentation with Swagger UI, CORS configuration, snake case naming strategy, domain event publishing and virtual threads.
- Manufacturing & Batch Management: production batches with their incidents and closing (`/api/v1/production-batches`).
- IoT Monitoring: equipment, sensors, readings and alerts (`/api/v1/equipment`, `/api/v1/sensors`, `/api/v1/readings`, `/api/v1/alerts`).
- Quality & Compliance: quality documents, deviations, evidence, CAPA plans and actions, analytical results, batch releases, audits and their findings, audit events, regulatory reports, collaboration tasks, task comments, traceability gaps and deviation trends.
- Organizations & Profiles: organization registration with RUC validation, facilities, user profiles with their notification preferences, administrative activities and contact inquiries (TS12).
- Subscriptions & Payments: plans and subscriptions with activation, renewal, cancellation and payment through the Niubiz gateway.
- Dockerfile and production profile to deploy the RESTful API with a MySQL database.
