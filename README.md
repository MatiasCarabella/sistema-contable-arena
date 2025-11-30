
# Accounting Management System

<div align="center">

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-9.2.1-02303A?logo=gradle&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)

</div>

An interactive console-based Java application for managing accounting operations: clients, suppliers, income, expenses, and financial reports.

## Quick Start

```bash
# 1. Start MySQL database
docker-compose up -d

# 2. Run the application
./gradlew run        # Unix/Linux/Mac
gradlew.bat run      # Windows

# 3. Stop database when done
docker-compose down
```

That's it! The database initializes automatically with sample data on first run.

## Features

- **Client Management** - Create, update, delete, search clients
- **Supplier Management** - Manage supplier information
- **Income Tracking** - Record income associated with clients
- **Expense Tracking** - Record expenses associated with suppliers
- **Financial Reports** - Transaction history and balance reports by date range

## Architecture

Layered architecture following SOLID principles:

```
View (Console UI) → Service (Business Logic) → Repository (Data Access) → MySQL Database
```

- **Model**: Domain entities (Client, Supplier, Transaction, Income, Expense)
- **Service**: Business logic with validation
- **Repository**: JDBC data access with proper transaction management
- **View**: Interactive console menus

## Configuration

The application uses environment variables for database configuration. Default values work with Docker setup:

```bash
DB_HOST=localhost
DB_PORT=3306
DB_NAME=accounting_system
DB_USER=root
DB_PASSWORD=root
```

## Gradle Commands

```bash
./gradlew build     # Build the project
./gradlew run       # Run the application (quiet mode by default)
./gradlew clean     # Clean build artifacts
./gradlew jar       # Create executable JAR
```

## Manual Database Setup

If not using Docker:

```bash
# Create database
mysql -u root -p < init.sql

# Set environment variables (see Configuration section)

# Run application
./gradlew run
```

## Project Structure

```
src/
├── config/         # Database configuration
├── exception/      # Custom exceptions
├── model/          # Domain entities
├── repository/     # Data access layer
├── service/        # Business logic layer
└── view/           # Console UI
```

## Notes

- Database runs in Docker for consistency
- Application runs locally for full interactivity
- All operations include validation and error handling
- Data persists in Docker volumes between runs