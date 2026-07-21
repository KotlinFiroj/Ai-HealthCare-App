package com.mediai.enterprise

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [MediAIApp]
 * The [Application] class for the MediAI Enterprise platform.
 * Initializes Hilt for Dependency Injection.
 */
@HiltAndroidApp
class MediAIApp : Application()
