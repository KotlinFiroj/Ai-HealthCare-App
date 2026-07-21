# Implementation Plan - Phase 3: Multi-Module Configuration & Convention Plugins

Formalize the multi-module build logic to reduce duplication and enforce architectural boundaries using **Gradle Convention Plugins**.

## User Review Required

> [!IMPORTANT]
> This phase will transition the project to use an **Included Build** (`build-logic`) for common Gradle configurations.
>
> - **Build Logic**: We will move repetitive configuration (compile SDK, JVM targets, Hilt setup, Compose setup) into custom plugins.
> - **Dependency Separation**: We will define clear rules for what modules can depend on others (e.g., `:core:network` should not depend on `:core:ui`).

## Proposed Changes

### Build Logic Structure

#### [NEW] [build-logic](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/build-logic)
- Create a new directory `build-logic` as an included build.
- Define a `convention` module within `build-logic`.

### Convention Plugins

#### [NEW] [AndroidLibraryConventionPlugin.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/build-logic/convention/src/main/kotlin/AndroidLibraryConventionPlugin.kt)
- Shared configuration for all Android library modules (CompileSdk 35, MinSdk 26, Java 17).

#### [NEW] [AndroidComposeConventionPlugin.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/build-logic/convention/src/main/kotlin/AndroidComposeConventionPlugin.kt)
- Centralized Jetpack Compose configuration.

#### [NEW] [AndroidHiltConventionPlugin.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/build-logic/convention/src/main/kotlin/AndroidHiltConventionPlugin.kt)
- Standardized Hilt and KSP setup.

#### [NEW] [AndroidApplicationConventionPlugin.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt)
- Shared configuration for the `:app` module.

### Refactoring Existing Modules

#### [MODIFY] [All core modules](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/)
- Update `build.gradle.kts` files to use the new convention plugins.
- Remove redundant boilerplate.

#### [MODIFY] [settings.gradle.kts](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/settings.gradle.kts)
- Include the `build-logic` build.

## Architecture & Module Rules

We will enforce these rules:
- `core:ui` depends on `core:designsystem`.
- `core:data` depends on `core:network`, `core:database`, and `core:domain`.
- `core:domain` is a pure Kotlin module (where possible) or minimal Android.

## Verification Plan

### Automated Tests
- Run `./gradlew assemble` to ensure all modules compile with the new convention plugins.
- Verify dependency graph using `./gradlew :app:dependencies`.

### Manual Verification
- Check that changing a version in `libs.versions.toml` correctly propagates through the convention plugins.
