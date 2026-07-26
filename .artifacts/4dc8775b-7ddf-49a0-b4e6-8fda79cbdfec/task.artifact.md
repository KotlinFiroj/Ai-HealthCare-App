# Tasks - Phase 36: Face Authentication & Advanced Biometrics

- `[x]` Update `BiometricAuthenticator` with Type Detection
    - `[x]` Detect Face vs Fingerprint support
    - `[x]` Support for `BIOMETRIC_STRONG` vs `BIOMETRIC_WEAK`
- `[x]` Update `user_prefs.proto`
    - `[x]` Add `biometric_enabled` and `biometric_type`
- `[x]` Implement Biometric Enrollment UI
    - `[x]` Create `BiometricEnrollmentScreen.kt`
    - `[x]` Add Face/Fingerprint visual indicators
- `[x]` Update Backend Models
    - `[x]` Add `biometric_verified` and `last_biometric_auth` to `User` model
- `[x]` Implement Backend Biometric Status API
    - `[x]` Update `UserResponse` schema
    - `[x]` Add `PATCH /me/biometric-status` endpoint
- `[x]` Integrate Enrollment Flow in Mobile App
- `[ ]` Verify Identity State Management
