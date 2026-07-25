package com.mediai.enterprise.feature.appointment.presentation.telehealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mediai.enterprise.core.designsystem.icon.MediAIIcons

@Composable
fun ConsultationRoomScreen(
    doctorName: String,
    onEndCall: () -> Unit
) {
    var isMicOn by remember { mutableStateOf(true) }
    var isVideoOn by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Main Video Feed (Doctor)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Connecting to $doctorName...",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Local Preview (User)
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
                .size(width = 100.dp, height = 150.dp)
                .clip(MaterialTheme.shapes.medium),
            color = Color.DarkGray
        ) {
            if (!isVideoOn) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(MediAIIcons.VideoOff, contentDescription = null, tint = Color.White)
                }
            }
        }

        // Call Controls
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            color = Color.White.copy(alpha = 0.2f),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { isMicOn = !isMicOn },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isMicOn) Color.White.copy(alpha = 0.3f) else Color.Red
                    )
                ) {
                    Icon(
                        imageVector = if (isMicOn) MediAIIcons.Mic else MediAIIcons.MicOff,
                        contentDescription = "Toggle Mic",
                        tint = if (isMicOn) Color.White else Color.White
                    )
                }

                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(MediAIIcons.EndCall, contentDescription = "End Call")
                }

                IconButton(
                    onClick = { isVideoOn = !isVideoOn },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isVideoOn) Color.White.copy(alpha = 0.3f) else Color.Red
                    )
                ) {
                    Icon(
                        imageVector = if (isVideoOn) MediAIIcons.Video else MediAIIcons.VideoOff,
                        contentDescription = "Toggle Video",
                        tint = if (isVideoOn) Color.White else Color.White
                    )
                }
            }
        }
    }
}
