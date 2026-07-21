package com.mediai.enterprise.core.designsystem.theme

import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Light color scheme for MediAI Enterprise.
 */
@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = Green40,
    onSecondary = Color.White,
    secondaryContainer = Green90,
    onSecondaryContainer = Green10,
    tertiary = Purple40,
    onTertiary = Color.White,
    tertiaryContainer = Purple90,
    onTertiaryContainer = Purple10,
    error = Color.Red, // Standardize if needed
    onError = Color.White,
    background = Gray99,
    onBackground = Gray10,
    surface = Gray99,
    onSurface = Gray10,
    surfaceVariant = Gray90,
    onSurfaceVariant = Gray20,
    outline = Gray20,
)

/**
 * Dark color scheme for MediAI Enterprise.
 */
@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    secondary = Green80,
    onSecondary = Green20,
    secondaryContainer = Green30,
    onSecondaryContainer = Green90,
    tertiary = Purple80,
    onTertiary = Purple20,
    tertiaryContainer = Purple30,
    onTertiaryContainer = Purple90,
    error = Color.Red, // Standardize if needed
    onError = Color.White,
    background = Gray10,
    onBackground = Gray90,
    surface = Gray10,
    onSurface = Gray90,
    surfaceVariant = Gray20,
    onSurfaceVariant = Gray90,
    outline = Gray90,
)

/**
 * [MediAITheme]
 * Main theme entry point for the MediAI Enterprise application.
 *
 * @param darkTheme Whether to use the dark theme palette.
 * @param dynamicColor Whether to use dynamic color (Android 12+).
 */
@Composable
fun MediAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MediAITypography,
        content = content
    )
}
