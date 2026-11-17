# Contributing Guide

Thank you for your interest in contributing to **DoofPlus Platform**! This guide describes how we work so that every change stays consistent with the project architecture, is easy to review and is safe to release.

> Before you start, read the [README](README.md) to understand the purpose, the architecture and how to run the project locally.

---

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Branching Model (GitFlow)](#branching-model-gitflow)
- [Commit Convention](#commit-convention)
- [Architecture and Code Guidelines](#architecture-and-code-guidelines)
- [REST API Design](#rest-api-design)
- [Testing](#testing)
- [Pull Requests](#pull-requests)
- [Release Process](#release-process)
- [Reporting Bugs and Suggesting Enhancements](#reporting-bugs-and-suggesting-enhancements)

---

## Code of Conduct

We expect respectful, professional and inclusive behavior in issues, pull requests and reviews. Criticism is aimed at the code, never at people.

---

## How Can I Contribute?

- Reporting bugs or unexpected API behavior.
- Suggesting new features aligned with the DoofPlus Web Application.
- Implementing features or fixing bugs.
- Improving tests, OpenAPI documentation or i18n messages.

---

## Development Setup

1. Fork or clone the repository:
   ```bash
   git clone https://github.com/IngesCompany/doofplus-platform.git
   cd doofplus-platform
   ```
2. Switch to `develop` (the integration branch):
   ```bash
   git checkout develop
   ```
3. Copy `.env.example` to `.env`, set your credentials and export the variables.
4. Check that everything builds and the tests pass:
   ```bash
   ./mvnw clean verify
   ```

**Requirements:** JDK 27, MySQL 8+ and an IDE with Lombok support (enable *annotation processing*).

---

## Branching Model (GitFlow)

| Branch | Purpose | Created from | Merged into |
|---|---|---|---|
| `main` | Production code. Every merge is a version. | — | — |
| `develop` | Integration of the next version. | `main` | — |
| `feature/<description>` | New feature or improvement. | `develop` | `develop` |
| `release/vX.Y.Z` | Version preparation (changelog, `pom.xml` version). | `develop` | `main` and `develop` |
| `hotfix/<description>` | Urgent production fix. | `main` | `main` and `develop` |

Rules:

- **Never** commit directly to `main` or `develop`.
- Branch names are in English and *kebab-case*, describing the change, optionally prefixed with the context:
  - `feature/iam-invitations`
  - `feature/monitoring-telemetry`
  - `feature/subscriptions-change-plan`
- Keep your branch up to date with `develop` before opening the PR.

---

## Commit Convention

We use **[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)**. Messages are written in **English**, in the **imperative mood** and in lowercase.

```
<type>(<scope>): <description>
```

**Allowed types**

| Type | Usage |
|---|---|
| `feat` | New feature |
| `fix` | Bug fix |
| `refactor` | Internal change that does not alter behavior |
| `test` | Add or fix tests |
| `docs` | Documentation |
| `chore` | Maintenance, build, releases |
| `perf` | Performance improvement |
| `ci` | Continuous integration |

**Scopes**: use the name of the affected bounded context:

`iam` · `organizations` · `subscriptions` · `manufacturing` · `monitoring` · `quality` · `shared` · `platform` (cross-cutting changes) · `release`

**Real examples from the repository**

```text
feat(iam): add the invitations of new members for administrators
feat(monitoring): receive the thingsboard telemetry
fix(subscriptions): answer 503 when niubiz is not available
refactor(organizations): rename profiles to user profiles as in the web app
chore(release): prepare version 1.3.0
```

If the change breaks API compatibility, add `!` after the scope and a `BREAKING CHANGE:` footer explaining the migration.

---

## Architecture and Code Guidelines

The project is a **modular monolith with DDD and CQRS**. Every contribution must respect the boundaries of each bounded context and the layer separation.

### Where Things Go

```
<context>/
├── domain/model/aggregates        → Aggregates (extend AbstractDomainAggregateRoot)
├── domain/model/entities          → Entities inside the aggregate
├── domain/model/valueobjects      → Value objects (preferably immutable records)
├── domain/model/commands          → Commands (records): CreateXCommand, UpdateXCommand…
├── domain/model/queries           → Queries (records): GetXByIdQuery, GetAllXQuery…
├── domain/model/events            → Domain events
├── domain/repositories            → Repository interfaces (ports)
├── application/commandservices    → XCommandService interfaces
├── application/queryservices      → XQueryService interfaces
├── application/internal/…         → *Impl implementations, event handlers, outbound services
├── infrastructure/persistence/jpa → *PersistenceEntity, *PersistenceRepository, *RepositoryImpl, *PersistenceAssembler
└── interfaces/rest                → XsController, resources (DTOs) and transform/ (assemblers)
    interfaces/acl                 → Facades exposed to other contexts
```

### Design Rules

1. **The domain does not depend on frameworks.** No JPA or Spring annotations in `domain/`. Persistence lives in `infrastructure/persistence/jpa` with its own entities and two-way *assemblers*.
2. **Business rules live in the aggregate.** Application services orchestrate; they do not decide. State transitions are validated inside the aggregate.
3. **Errors as values.** Command services return `Result<T, ApplicationError>`. Use the `ApplicationError` factory methods:
   - `validationError(...)` → 400
   - `notFound(...)` → 404
   - `conflict(...)` / `invalidState(...)` → 409
   - `businessRuleViolation(...)` → 422
   - `unauthorized(...)`

   Controllers convert the result with `ResponseEntityAssembler.toResponseEntityFromResult(...)`. Do not throw exceptions for expected business flows.
4. **No direct dependencies between contexts.** If a context needs data from another one, it uses that context's facade in `interfaces/acl` (such as `IamContextFacade`) or reacts to its domain events. Never import repositories or aggregates from another context.
5. **One aggregate per transaction.** Coordinate several aggregates through events (`DomainEventPublisher`).
6. **External integrations** (Niubiz, ThingsBoard…) are hidden behind an interface, and their implementation lives in `infrastructure/`.
7. **Null safety.** Annotate new packages/classes with `@NullMarked` (JSpecify), following the style of the shared kernel.

### Naming Conventions

| Element | Convention | Example |
|---|---|---|
| Controller | Plural + `Controller` | `DeviationsController` |
| Input resource | `Create/Update` + name + `Resource` | `CreateDeviationResource` |
| Output resource | Name + `Resource` | `DeviationResource` |
| REST assembler | `<Target>From<Source>Assembler` | `CreateDeviationCommandFromResourceAssembler` |
| JPA entity | Name + `PersistenceEntity` | `DeviationPersistenceEntity` |
| Repository adapter | Name + `RepositoryImpl` | `DeviationRepositoryImpl` |
| Service | Name + `CommandService` / `QueryService` (+ `Impl`) | `DeviationCommandServiceImpl` |

Tables are named automatically in plural `snake_case` (`SnakeCaseWithPluralizedTablePhysicalNamingStrategy`); do not use `@Table(name = ...)` unless it is strictly necessary.

### Code Style

- Modern Java: `record`, `sealed`, *pattern matching*, and `var` when it improves readability.
- 4-space indentation, no wildcard imports (`*`).
- Code, identifiers, Javadoc and comments **in English**.
- Document public interfaces, aggregates and endpoints with Javadoc.
- Use Lombok only where it adds value (`@Getter`, constructors); avoid `@Data` on entities.

### Internationalization

Every client-facing message must exist in **English and Spanish**:

- `messages.properties` / `messages_es.properties` → API messages.
- `ValidationMessages.properties` / `ValidationMessages_es.properties` → Bean Validation messages.

---

## REST API Design

- Routes under `/api/v1`, **plural** nouns in **kebab-case** (`/capa-actions`).
- Semantic HTTP verbs: `GET` (read), `POST` (create → `201 Created`), `PUT /{id}` (update), `DELETE /{id}` (delete).
- Filters as query params (`?batchCode=`, `?organizationId=`), not as nested routes by internal id.
- Enums are **returned in `UPPER_SNAKE_CASE`** and **accepted in `kebab-case`**.
- Errors in **RFC 9457 Problem Details** format, with the status code semantics described in the README.
- Document every endpoint with OpenAPI annotations (`@Tag`, `@Operation`, `@ApiResponse`) and check the result in Swagger UI.
- If the endpoint requires a role, update `WebSecurityConfiguration` in the `iam` context.
- Keep compatibility with the Web Application: any contract change must be coordinated with the frontend team and reflected in the changelog.

---

## Testing

Every contribution must include tests and leave the suite green:

```bash
./mvnw test
```

| What you change | Expected tests |
|---|---|
| Aggregate / value object | Domain unit tests (invariants and state transitions) |
| Command service | Use case tests with repository doubles, covering `Success` and every `Failure` |
| Controller / contract | Contract or web layer test |
| External integration | Adapter test with the external dependency mocked |

Conventions:

- The package structure of `src/test` mirrors `src/main`.
- Class name: `<TestedClass>Test`.
- Tests must not depend on real external services (Niubiz, ThingsBoard) or on execution order.

---

## Pull Requests

1. Create your branch from `develop` following the naming convention.
2. Make small, atomic commits following Conventional Commits.
3. Update your branch with `develop` and run `./mvnw clean verify`.
4. Add your change to the **`[Unreleased]`** section of [`CHANGELOG.md`](CHANGELOG.md) (`Added`, `Changed`, `Fixed`, `Removed`…).
5. Open the PR **against `develop`** with a title in Conventional Commit format.

### Suggested Template

```markdown
## Description
What changes and why?

## Affected bounded context
iam | organizations | subscriptions | manufacturing | monitoring | quality | shared

## Type of change
- [ ] feat
- [ ] fix
- [ ] refactor
- [ ] breaking change

## How to test
Endpoints, sample payloads or steps in Swagger UI.

## Checklist
- [ ] Respects the DDD layers and does not couple contexts to each other
- [ ] Use cases return a `Result` with the right `ApplicationError`
- [ ] Includes tests and `./mvnw clean verify` passes
- [ ] Messages added in English and Spanish
- [ ] Endpoints documented in OpenAPI
- [ ] `CHANGELOG.md` updated under `[Unreleased]`
```

### Review

- At least **one approval** is required before merging.
- The author addresses the comments; the reviewer resolves the conversation.
- `feature/*` branches are merged into `develop` with a *merge commit* and deleted afterwards.

---

## Release Process

Performed by the maintainers:

1. Create `release/vX.Y.Z` from `develop`.
2. Update `<version>` in `pom.xml` and move the `[Unreleased]` entries to `[X.Y.Z] - YYYY-MM-DD` in `CHANGELOG.md`.
3. Commit: `chore(release): prepare version X.Y.Z`.
4. Merge into `main` and `develop`, and tag `vX.Y.Z`.
5. Deploy on Render (Manual Deploy of `main`) and check Swagger UI in production.

Versions follow **SemVer**: `MAJOR` for breaking API changes, `MINOR` for backward-compatible features, `PATCH` for fixes.

---

## Reporting Bugs and Suggesting Enhancements

Open a GitHub *issue* including:

**Bugs**
- Endpoint and HTTP method.
- Payload sent and response received (include the Problem Details `code`).
- Expected behavior.
- Version (`/v3/api-docs` → `info.version`) and environment (local / production).

**Enhancements**
- Problem it solves and affected context.
- Proposed contract (endpoint, request and response).
- Impact on the Web Application.

> [!CAUTION]
> If you find a security vulnerability, **do not open a public issue**. Contact the IngesCompany maintainers directly.

---

Thank you for helping build DoofPlus! 💊