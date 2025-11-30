<div align="center">

# 📊 Accounting Management System

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-9.2.1-02303A?logo=gradle&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-9.3.0-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)

</div>

**A professional console-based accounting system with colorful CLI, built with clean architecture and SOLID principles.**

## Prerequisites

- **Java 25** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Docker** - [Install Docker](https://docs.docker.com/get-docker/)
- **Git** - [Install Git](https://git-scm.com/downloads)

## Quick Start

```bash
docker-compose up -d    # Start MySQL
./gradlew run           # Run application
```

> 💡 Database auto-initializes with sample data on first run.

## Features

| Feature | Description |
|---------|-------------|
| **Clients** | Full CRUD operations with search |
| **Suppliers** | Manage supplier information |
| **Income** | Track revenue by client |
| **Expenses** | Record costs by supplier |
| **Reports** | Financial summaries and balance sheets |

## Architecture

Clean layered architecture following **SOLID principles**:

```
┌──────────────┐
│     View     │  Interactive CLI with colors
├──────────────┤
│  Controller  │  Input validation
├──────────────┤
│   Service    │  Business logic
├──────────────┤
│  Repository  │  JDBC data access
├──────────────┤
│    MySQL     │  Persistent storage
└──────────────┘
```

**Key Components:**
- **View** - Colorful console interface
- **Controller** - Input validation and request handling
- **Service** - Business logic and entity coordination
- **Repository** - Data access with transactions
- **Model** - Domain entities (Client, Supplier, Transaction)

## Project Structure

```
src/
├── config/      Database configuration
├── controller/  Input validation
├── exception/   Custom exceptions
├── model/       Domain entities
├── repository/  Data access (JDBC)
├── service/     Business logic
└── view/        Console UI
```

## Commands

```bash
# Development
./gradlew run            # Run application
./gradlew build          # Build project
./gradlew spotlessApply  # Format code

# Docker
docker-compose up -d     # Start MySQL
docker-compose down      # Stop MySQL
docker logs accounting_mysql  # View logs
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

