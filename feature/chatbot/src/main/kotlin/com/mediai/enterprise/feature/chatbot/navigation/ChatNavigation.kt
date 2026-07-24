package com.mediai.enterprise.feature.chatbot.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.CHAT_ROUTE
import com.mediai.enterprise.feature.chatbot.presentation.chat.ChatRoute

fun NavGraphBuilder.chatGraph(
    navController: NavController
) {
    composable(route = CHAT_ROUTE) {
        ChatRoute(
            onBack = { navController.popBackStack() }
        )
    }
}
