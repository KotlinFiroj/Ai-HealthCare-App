package com.mediai.enterprise.feature.ai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.feature.ai.domain.model.UrgencyLevel

@Composable
fun UrgencyBanner(
    level: UrgencyLevel,
    onEmergencyClick: () -> Unit
) {
    val (bgColor, textColor, label) = when (level) {
        UrgencyLevel.LOW -> Triple(Color.Green.copy(alpha = 0.1f), Color.DarkGray, "Low Urgency")
        UrgencyLevel.MEDIUM -> Triple(Color.Yellow.copy(alpha = 0.1f), Color.DarkGray, "Medium Urgency")
        UrgencyLevel.HIGH -> Triple(Color.Red.copy(alpha = 0.1f), Color.Red, "High Urgency")
        UrgencyLevel.EMERGENCY -> Triple(Color.Red, Color.White, "EMERGENCY")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = textColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                if (level == UrgencyLevel.EMERGENCY) {
                    Text(
                        text = "Potential life-threatening condition detected.",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )
                }
            }
            if (level == UrgencyLevel.EMERGENCY) {
                Button(
                    onClick = onEmergencyClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Red)
                ) {
                    Text("SOS")
                }
            }
        }
    }
}
