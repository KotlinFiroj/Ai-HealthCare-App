package com.mediai.enterprise.core.designsystem.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.designsystem.component.MediAIButton
import com.mediai.enterprise.core.designsystem.component.MediAIOutlinedButton

/**
 * Preview for the Design System components and theme.
 */
@Preview(showBackground = true, name = "Light Theme")
@Composable
fun ThemePreviewLight() {
    MediAITheme(darkTheme = false) {
        ThemePreviewContent()
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun ThemePreviewDark() {
    MediAITheme(darkTheme = true) {
        ThemePreviewContent()
    }
}

@Composable
private fun ThemePreviewContent() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "MediAI Design System", style = MaterialTheme.typography.headlineMedium)

            Text(text = "Buttons", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MediAIButton(onClick = {}) {
                    Text("Primary")
                }
                MediAIOutlinedButton(onClick = {}) {
                    Text("Outlined")
                }
            }

            Text(text = "Typography", style = MaterialTheme.typography.titleLarge)
            Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Label Small", style = MaterialTheme.typography.labelSmall)
        }
    }
}
