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
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mediai.enterprise.core.designsystem.theme.MediAITheme
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.core.navigation.REMINDERS_ROUTE
import com.mediai.enterprise.core.navigation.EMERGENCY_ROUTE
import com.mediai.enterprise.feature.appointment.navigation.appointmentGraph
import com.mediai.enterprise.feature.emergency.navigation.emergencyGraph
import com.mediai.enterprise.feature.auth.navigation.authGraph
import com.mediai.enterprise.feature.home.navigation.homeGraph
import com.mediai.enterprise.feature.home.navigation.navigateToHome
import com.mediai.enterprise.feature.reminder.navigation.reminderGraph
import com.mediai.enterprise.feature.reports.navigation.reportGraph
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
                                navController.navigateToHome(
                                    navOptions = NavOptions.Builder()
                                        .setPopUpTo(MediAINavDestinations.AUTH_ROUTE, inclusive = true)
                                        .build()
                                )
                            },
                            onNavigateToRegister = {
                                // navController.navigate("register")
                            }
                        )

                        homeGraph(
                            onNavigateToAppointments = {
                                navController.navigate(MediAINavDestinations.APPOINTMENTS_ROUTE)
                            },
                            onNavigateToReports = {
                                navController.navigate(MediAINavDestinations.REPORTS_ROUTE)
                            },
                            onNavigateToReminders = {
                                navController.navigate(REMINDERS_ROUTE)
                            },
                            onNavigateToEmergency = {
                                navController.navigate(EMERGENCY_ROUTE)
                            }
                        )

                        appointmentGraph(navController)

                        reportGraph(navController)

                        reminderGraph(navController)

                        emergencyGraph(navController)
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
