# MediAI Enterprise: Security Guide

## Data at Rest
- **Database Encryption**: All local health records are stored in a **SQLCipher** encrypted Room database using AES-256.
- **Hardware-Backed Key Management**:
    - The database passphrase is never stored in plain text.
    - We use the **Android Keystore** to generate a 256-bit AES key.
    - This key is stored in the device's Secure Element (SE) or Trusted Execution Environment (TEE), making it non-extractable even with root access.

## Secure PII Handling
- **Logs**: Timber is configured with a custom `CrashlyticsTree` that filters out sensitive information before sending logs to the server.
- **Preferences**: Token management uses `EncryptedSharedPreferences` for additional layer of protection.
- **Biometric Identity**: Securely integrated using the `BiometricPrompt` API, ensuring that only the authorized user can access sensitive health records on the device.

## Data in Transit
- **JWT Authentication**: Secure stateless authentication with automatic token refresh.
- **Certificate Pinning**: (Planned) To prevent Man-in-the-Middle (MitM) attacks.

## Identity & Privacy
- **Biometric Authentication**: Integrated Fingerprint and Face Unlock using the Android Biometric API.
- **PII Protection**: Personally Identifiable Information is treated as high-sensitivity data throughout the pipeline.
