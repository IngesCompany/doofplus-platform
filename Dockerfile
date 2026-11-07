# Dockerfile for doofplus-platform
# Builds the DoofPlus RESTful API with Maven and runs it on a lightweight Java 27 runtime.
# Multi-stage build: the first stage compiles the jar and the second one only runs it.

# Step 1: Build the application with Maven and Temurin JDK 27
FROM maven:3.10.0-eclipse-temurin-27 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime image with the production profile
FROM eclipse-temurin:27-jre AS runtime
ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Step 3: Expose the port and run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Environment variables required in the hosting provider (Render) for the prod profile:
# - DATABASE_URL: database host name or address (Railway).
# - DATABASE_PORT: database port.
# - DATABASE_NAME: database name.
# - DATABASE_USER: database user.
# - DATABASE_PASSWORD: database password.
# - PORT: port where the application listens (default 8080).
