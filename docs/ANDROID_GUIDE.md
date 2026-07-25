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
- **Workflows**:
    - `android.yml`: Quality checks and debug builds on every push.
    - `release.yml`: Tag-based production builds and automated distribution.
