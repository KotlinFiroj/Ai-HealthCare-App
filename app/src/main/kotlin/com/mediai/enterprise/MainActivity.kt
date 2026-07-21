package com.mediai.enterprise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mediai.enterprise.core.designsystem.theme.MediAITheme
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.auth.navigation.authGraph
import dagger.hilt.android.AndroidEntryPoint

/**
 * [MainActivity]
 * The entry point of the application.
 * Uses Jetpack Compose for the UI.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediAITheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MediAINavDestinations.AUTH_ROUTE,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        authGraph(
                            onLoginSuccess = {
                                // Navigate to Home
                            },
                            onNavigateToRegister = {
                                // navController.navigate("register")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to $name!",
        modifier = modifier
    )
}
