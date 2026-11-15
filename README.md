# DoofPlus Platform

## Deployment

- **API:** https://doofplus-platform.onrender.com/api/v1 (Render, Docker image of `main`; deployed with Manual Deploy after each release).
- **Swagger UI:** https://doofplus-platform.onrender.com/swagger-ui.html
- **Database:** MySQL on Railway, read from `DATABASE_URL`, `DATABASE_PORT`, `DATABASE_NAME`, `DATABASE_USER` and `DATABASE_PASSWORD`.
- **JWT:** set `JWT_SECRET` (at least 32 characters) in the environment; the default value is only for development.
- The free plan of Render sleeps the service when it is not used, so the first request can take about a minute.

DoofPlus Backend

### Reference Documentation

3.0.0