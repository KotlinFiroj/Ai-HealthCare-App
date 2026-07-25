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
1. **Unidirectional Data Flow (UDF)**: ViewModels expose a single `uiState` flow.
2. **Dependency Injection**: Use Hilt for all constructor injection to maintain testability.
3. **Async Programming**: Coroutines for background tasks and Flow for reactive streams.
