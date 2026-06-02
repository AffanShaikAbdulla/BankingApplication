# Use Eclipse Temurin JDK 17 as base image (official, reliable)
FROM eclipse-temurin:17-jdk-alpine
LABEL org.opencontainers.image.authors="Shaik Abdulla <skabdulla0298@gmail.com>"

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from target directory
COPY target/accounts-0.0.1-SNAPSHOT.jar app.jar

# Copy application.properties if needed (already inside JAR, so optional)
# COPY src/main/resources/application.properties /app/config/

# Expose port 8080
EXPOSE 8080

# Health check (optional but good practice)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]