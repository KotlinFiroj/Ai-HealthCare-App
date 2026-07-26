package com.mediai.enterprise.core.ui.util

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * [LocaleHelper]
 * Utility to manage and apply app-specific language settings.
 */
object LocaleHelper {

    /**
     * Applies a new locale to the application.
     * Uses the modern [LocaleManager] on Android 13+ and [AppCompatDelegate] for older versions.
     */
    fun applyLocale(context: Context, languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(languageCode)
        } else {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    /**
     * Gets the currently applied language code.
     */
    fun getLanguageCode(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales[0]?.language ?: "en"
        } else {
            AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"
        }
    }
}
