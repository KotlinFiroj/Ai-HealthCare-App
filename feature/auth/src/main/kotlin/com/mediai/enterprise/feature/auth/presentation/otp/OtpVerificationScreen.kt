package com.mediai.enterprise.feature.auth.presentation.otp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mediai.enterprise.core.designsystem.component.MediAIButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    onVerifyClick: (String) -> Unit,
    onResendClick: () -> Unit,
    onBack: () -> Unit
) {
    var otpCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Security Verification") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enter 2FA Code",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "We've sent a 6-digit verification code to your registered email.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = otpCode,
                onValueChange = { if (it.length <= 6) otpCode = it },
                modifier = Modifier.width(200.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    letterSpacing = 8.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                placeholder = { Text("000000") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            MediAIButton(
                onClick = { onVerifyClick(otpCode) },
                modifier = Modifier.fillMaxWidth(),
                enabled = otpCode.length == 6
            ) {
                Text("Verify & Continue")
            }

            TextButton(onClick = onResendClick) {
                Text("Resend Code")
            }
        }
    }
}
