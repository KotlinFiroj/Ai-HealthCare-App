# Tasks - Phase 3: Multi-Module Configuration & Convention Plugins

- `[x]` Setup `build-logic` Included Build
    - `[x]` Create `build-logic/settings.gradle.kts`
    - `[x]` Create `build-logic/gradle.properties`
    - `[x]` Create `build-logic/convention/build.gradle.kts`
- `[x]` Implement Convention Plugins
    - `[x]` `AndroidApplicationConventionPlugin`
    - `[x]` `AndroidLibraryConventionPlugin`
    - `[x]` `AndroidComposeConventionPlugin`
    - `[x]` `AndroidHiltConventionPlugin`
    - `[x]` `AndroidRoomConventionPlugin`
- `[x]` Register `build-logic` in root `settings.gradle.kts`
- `[x]` Refactor Modules to use Convention Plugins
    - `[x]` `:app`
    - `[x]` `:core:*`
- `[ ]` Verify Build and Sync
