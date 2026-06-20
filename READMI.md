### 🌊 Atlantis

Atlantis is a modern educational platform featuring AI-generated content, gamification, and a multidisciplinary approach to learning. It combines history, mathematics, biology, and other subjects in an engaging interactive environment.

### 🎯 Key Features

- 🎓 Multidisciplinary — history, mathematics, biology, and more
- 🤖 AI-Generated Content — lessons, tests, and tasks created with Spring AI
- 🏆 Gamification — XP, levels, streaks, achievements, and daily quests
- 📊 Progress Tracking — monitor success across subjects
- 🌊 Modern Stack — Java 25, Spring Boot 3.5, Vaadin 24.5, Kafka, PostgreSQL, Redis

### 🛠️ Tech Stack

- Backend: Java 25, Spring Boot 3.5.11
- Frontend: Vaadin 24.5
- Database: PostgreSQL, Redis
- Messaging: Apache Kafka
- AI: Spring AI (OpenAI)
- Build: Gradle (Multi-module)
- Integrations: REST, gRPC

### 📁 Architecture

| Directory | Description |
|-------|------------|
| `backend/` | Services, DB, Kafka, AI |
| `frontend/` | Vaadin UI |
| `starter/` | Entry point |
| `docs/` | Documentation |

### 🚀 Quick Start

#### Prerequisites:
- Java 25+
- Gradle 8.10+
- Docker (optional, for local infrastructure)

#### Run locally:
```bash
./gradlew clean build
./gradlew :starter:bootRun
```

#### Run with Docker Compose:
```bash
docker-compose up -d
./gradlew clean build
java -jar starter/build/libs/atlantis.jar
```

### 🌐 Access the application:

- Application: http://localhost:8080
- Actuator: http://localhost:8080/actuator

### 📋 Development Workflow

This project follows Git Flow with the following branches:
- `master` — stable releases
- `develop` — active development
- `feature/*` — new features

### 📊 Status

🔄 MVP in progress