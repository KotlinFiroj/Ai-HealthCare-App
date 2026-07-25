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
        // Start the app and perform basic actions to capture hot code paths
        pressHome()
        startActivityAndWait()

        // Example: Navigate through critical screens
        // Note: Real interactions depend on UI availability during generation
    }
}
