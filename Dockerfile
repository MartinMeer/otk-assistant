# Multi-stage build for optimized image size
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradle/ ./gradle/
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradlew.bat ./

# Make gradlew executable
RUN chmod +x ./gradlew

# Download dependencies (this layer will be cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code and config
COPY src/ ./src/
COPY config/ ./config/

# Build the application (skip checkstyle and tests for faster Docker builds)
RUN ./gradlew clean build --no-daemon -x test -x checkstyleMain

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Install wget for health checks
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Create non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the built JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create logs directory with proper permissions
RUN mkdir -p /app/logs && chown -R appuser:appuser /app

USER appuser

# Expose port (default Spring Boot port)
EXPOSE 8080

# Health check (using wget as it's more lightweight and commonly available)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Set JVM options for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]