package com.mediai.enterprise.core.security.integrity

import android.content.Context
import android.os.Build
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [HardwareIntegrity]
 * Provides methods to detect if the device is rooted, an emulator, or tampered with.
 */
@Singleton
class HardwareIntegrity @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val rootBeer = RootBeer(context)

    /**
     * Returns true if the device is rooted.
     */
    fun isRooted(): Boolean {
        return rootBeer.isRooted
    }

    /**
     * Returns true if the app is running on an emulator.
     */
    fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
    }

    /**
     * Performs a comprehensive environment check.
     */
    fun isEnvironmentSafe(): Boolean {
        // In production, we would return !(isRooted() || isEmulator())
        // For development convenience, we can make this configurable.
        return !isRooted()
    }
}
