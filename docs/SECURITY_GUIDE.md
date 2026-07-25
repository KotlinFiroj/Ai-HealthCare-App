# MediAI Enterprise: Security Guide

## Data at Rest
- **Database Encryption**: All local health records are stored in a **SQLCipher** encrypted Room database using AES-256.
- **Key Management**: Encryption keys are managed by the **Android Keystore**, ensuring they are hardware-backed and non-extractable.
- **Sensitive Prefs**: Tokens and session data are stored in `EncryptedSharedPreferences`.

## Data in Transit
- **JWT Authentication**: Secure stateless authentication with automatic token refresh.
- **Certificate Pinning**: (Planned) To prevent Man-in-the-Middle (MitM) attacks.

## Identity & Privacy
- **Biometric Authentication**: Integrated Fingerprint and Face Unlock using the Android Biometric API.
- **PII Protection**: Personally Identifiable Information is treated as high-sensitivity data throughout the pipeline.
