# Implementation Plan - Phase 4: Design System

Implement the foundational visual identity and reusable UI components for **MediAI Enterprise** using Material 3.

## User Review Required

> [!IMPORTANT]
> This phase defines the look and feel of the entire platform.
>
> - **Theme Selection**: We will use a healthcare-inspired palette: Primary Teal/Blue for trust, and Secondary Green for health/wellness.
> - **Dynamic Color**: Support for Android 12+ dynamic color schemes.
> - **Accessibility**: Ensuring all color combinations meet WCAG contrast requirements.

## Proposed Changes

### Core Design System (`:core:designsystem`)

#### [NEW] [Color.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/theme/Color.kt)
- Define light and dark color schemes.
- Define custom extended colors (e.g., success, warning, error specific to medical contexts).

#### [NEW] [Type.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/theme/Type.kt)
- Configure Material 3 Typography (Display, Headline, Title, Body, Label).

#### [NEW] [Theme.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/theme/Theme.kt)
- Create `MediAITheme` composable.
- Handle Light Mode, Dark Mode, and Dynamic Color.

#### [NEW] [Icons.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/icon/MediAIIcons.kt)
- Centralize all Material Icons used in the app (e.g., `HealthAndSafety`, `MedicalServices`, `Person`).

#### [NEW] [Background.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/component/Background.kt)
- Create a standard `MediAIBackground` for all screens.

#### [NEW] [Button.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/core/designsystem/src/main/kotlin/com/mediai/enterprise/core/designsystem/component/Button.kt)
- Implement enterprise-grade buttons (Primary, Outlined, Text).

### App Module Updates

#### [MODIFY] [MainActivity.kt](file:///J:/Android/AndroidStudioProjects/Gemini/Ai-HealthCare-App/app/src/main/kotlin/com/mediai/enterprise/MainActivity.kt)
- Wrap the UI with `MediAITheme`.

## Verification Plan

### Automated Tests
- **Compose Previews**: Implement previews for all theme variations and components.
- **Screenshot Tests (Placeholder)**: Setup structure for future screenshot testing.

### Manual Verification
- Verify Light and Dark mode switching in the IDE Preview.
- Check contrast ratios for primary actions.
