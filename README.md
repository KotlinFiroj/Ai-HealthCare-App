# MediAI Enterprise

AI-Powered Healthcare Platform for Android.

## Project Overview

MediAI Enterprise is a production-grade Android application designed for learning and demonstrating enterprise-level software engineering, AI integration, and healthcare domain expertise.

### Key Features
- **AI Medical Chatbot**: RAG-based chatbot using Gemini 2.5.
- **Prescription OCR**: Extracting medical data from images.
- **Health Monitoring**: Tracking vitals and daily goals.
- **Enterprise Security**: Biometric authentication, PII protection, and encrypted local storage.
- **Offline First**: Robust sync and local-first data architecture.

## Architecture

The project follows **Clean Architecture** principles and is modularized by feature and core layers.

### Multi-Module Structure
- `:app`: Main entry point and DI root.
- `:feature:*`: Independent feature modules (e.g., `:feature-auth`, `:feature-home`).
- `:core:*`: Shared infrastructure modules (e.g., `:core-data`, `:core-network`, `:core-ai`).

### Tech Stack
- **Kotlin**: Primary programming language.
- **Jetpack Compose**: Modern declarative UI.
- **Hilt**: Dependency Injection.
- **Room & DataStore**: Local persistence.
- **Retrofit & OkHttp**: Networking.
- **Gemini SDK**: AI capabilities.
- **Coroutines & Flow**: Asynchronous programming.

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- JDK 17.
- Gemini API Key (for AI features).

### Development Environment Setup
1. Clone the repository.
2. Set up your Gemini API key in `local.properties`:
   ```properties
   GEMINI_API_KEY=your_api_key_here
   ```
3. Build and run the project.

## Code Quality
We use the following tools to ensure high code quality:
- **Detekt**: Static code analysis.
- **ktlint**: Code formatting.
- **JUnit & Espresso**: Testing.

Run quality checks locally:
```bash
./gradlew detekt ktlintCheck
```

## Documentation
Detailed guides for each phase of the project can be found in the `docs/` directory (coming soon).
