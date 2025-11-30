# Multi-stage build for Java application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy source code and dependencies
COPY src ./src
COPY lib ./lib

# Compile the application
RUN javac -d bin -cp "lib/*" \
    src/exception/*.java \
    src/config/*.java \
    src/model/*.java \
    src/repository/*.java \
    src/service/*.java \
    src/controller/*.java \
    src/view/*.java \
    src/App.java

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy compiled classes and dependencies from builder
COPY --from=builder /app/bin ./bin
COPY --from=builder /app/lib ./lib

# Set environment variables with defaults
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=accounting_system
ENV DB_USER=root
ENV DB_PASSWORD=root

# Run the application
CMD ["java", "-cp", "bin:lib/*", "App"]
