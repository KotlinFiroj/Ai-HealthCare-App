package com.mediai.enterprise.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * [BaselineProfileGenerator]
 * Generates a Baseline Profile for the app to improve startup and frame performance.
 */
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "com.mediai.enterprise",
        includeInStartupProfile = true
    ) {
        // 1. Startup Flow
        pressHome()
        startActivityAndWait()

        // 2. Dashboard Interaction
        device.waitForIdle()

        // 3. Simulated User Journey
        // In a full production setup, we would use UI Automator here to navigate
        // to Chat, Reports, and Appointments to capture performance paths.
    }
}
