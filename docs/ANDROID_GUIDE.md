# MediAI Enterprise: Android Development Guide

## Build System
- **Gradle**: Kotlin DSL (`.kts`).
- **Version Catalog**: `libs.versions.toml` for centralized dependency management.
- **Convention Plugins**: Located in `build-logic`, these plugins standardize module configurations (Compose, Hilt, Room).

## Coding Standards
- **Language**: Kotlin.
- **Formatting**: ktlint enforced via `.editorconfig`.
- **Static Analysis**: Detekt with strict complexity and performance rules.

## Testing Strategy
- **Unit Tests**: MockK and JUnit 4.
- **UI Tests**: Compose Test Rule and Espresso.
- **Coverage**: JaCoCo reports generated for every PR.

## CI/CD
- **Platform**: GitHub Actions.
- **Verification Workflow**: Automated parallel jobs for Lint, Test, and Build on every PR.
- **Release Workflow**: Automated tagging, release note generation, and distribution to Firebase App Distribution upon `v*` tag push.

## Best Practices
1. **Unidirectional Data Flow (UDF)**: ViewModels expose a single `uiState` flow, ensuring predictable UI state management and easier debugging.
2. **Dependency Injection**: Use Hilt for all constructor injection to maintain testability and modularity across the 20+ feature and core modules.
3. **Async Programming**: Coroutines for non-blocking background tasks and Flow for reactive streams of health data.
4. **Offline First**: All healthcare features are designed to function without an internet connection, with background sync (WorkManager) handling data consistency.
