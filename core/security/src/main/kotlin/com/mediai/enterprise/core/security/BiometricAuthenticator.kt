package com.mediai.enterprise.core.security

import android.content.Context
import android.content.pm.PackageManager
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * [BiometricAuthenticator]
 * Handles biometric authentication requests and hardware detection.
 */
class BiometricAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /**
     * Checks if biometric authentication is available on the device.
     */
    fun isBiometricAvailable(authenticators: Int = BIOMETRIC_STRONG): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS
    }

    /**
     * Detects the type of biometric hardware available.
     * Note: On Android, multiple types can exist. This returns a primary hint.
     */
    fun getAvailableBiometricType(): BiometricType {
        val packageManager = context.packageManager
        return when {
            packageManager.hasSystemFeature(PackageManager.FEATURE_FACE) -> BiometricType.FACE
            packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) -> BiometricType.FINGERPRINT
            else -> BiometricType.NONE
        }
    }

    /**
     * Shows the biometric prompt.
     *
     * @param activity The host activity.
     * @param title Prompt title.
     * @param subtitle Prompt subtitle.
     * @param authenticators Security class (Strong vs Weak).
     * @param onResult Callback for success or failure.
     */
    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        authenticators: Int = BIOMETRIC_STRONG,
        onResult: (Boolean) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onResult(true)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onResult(false)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onResult(false)
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(authenticators)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

enum class BiometricType {
    FACE, FINGERPRINT, NONE
}
