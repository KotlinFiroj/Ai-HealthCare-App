package com.mediai.enterprise.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val CHAT_ROUTE = "chat"

fun NavController.navigateToChat(navOptions: NavOptions? = null) {
    this.navigate(CHAT_ROUTE, navOptions)
}
