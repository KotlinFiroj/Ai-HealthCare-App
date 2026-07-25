package com.mediai.enterprise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mediai.enterprise.core.designsystem.theme.MediAITheme
import com.mediai.enterprise.core.navigation.ANALYTICS_DASHBOARD_ROUTE
import com.mediai.enterprise.core.navigation.CHAT_ROUTE
import com.mediai.enterprise.core.navigation.EMERGENCY_ROUTE
import com.mediai.enterprise.core.navigation.HEALTH_COACH_ROUTE
import com.mediai.enterprise.core.navigation.HEALTH_TIMELINE_ROUTE
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.core.navigation.REMINDERS_ROUTE
import com.mediai.enterprise.core.navigation.RISK_PREDICTION_ROUTE
import com.mediai.enterprise.core.navigation.SYMPTOM_CHECKER_ROUTE
import com.mediai.enterprise.feature.ai.navigation.aiGraph
import com.mediai.enterprise.feature.analytics.navigation.analyticsGraph
import com.mediai.enterprise.feature.appointment.navigation.appointmentGraph
import com.mediai.enterprise.feature.auth.navigation.authGraph
import com.mediai.enterprise.feature.chatbot.navigation.chatGraph
import com.mediai.enterprise.feature.emergency.navigation.emergencyGraph
import com.mediai.enterprise.feature.healthtimeline.navigation.healthTimelineGraph
import com.mediai.enterprise.feature.home.navigation.homeGraph
import com.mediai.enterprise.feature.home.navigation.navigateToHome
import com.mediai.enterprise.feature.reminder.navigation.reminderGraph
import com.mediai.enterprise.feature.reports.navigation.reportGraph
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.Modifier

/**
 * [MainActivity]
 * The entry point of the application.
 * Manages the top-level navigation graph and applies the global design theme.
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
                            },
                            onNavigateToTimeline = {
                                navController.navigate(HEALTH_TIMELINE_ROUTE)
                            },
                            onNavigateToChat = {
                                navController.navigate(CHAT_ROUTE)
                            },
                            onNavigateToSymptomChecker = {
                                navController.navigate(SYMPTOM_CHECKER_ROUTE)
                            },
                            onNavigateToRiskPrediction = {
                                navController.navigate(RISK_PREDICTION_ROUTE)
                            },
                            onNavigateToHealthCoach = {
                                navController.navigate(HEALTH_COACH_ROUTE)
                            },
                            onNavigateToAnalytics = {
                                navController.navigate(ANALYTICS_DASHBOARD_ROUTE)
                            }
                        )

                        appointmentGraph(navController)
                        reportGraph(navController)
                        reminderGraph(navController)
                        emergencyGraph(navController)
                        healthTimelineGraph(navController)
                        chatGraph(navController)
                        aiGraph(navController)
                        analyticsGraph(navController)
                    }
                }
            }
        }
    }
}
