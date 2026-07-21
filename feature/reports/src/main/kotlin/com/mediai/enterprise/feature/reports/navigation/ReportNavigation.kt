package com.mediai.enterprise.feature.reports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.reports.presentation.scan.ScanScreen
import com.mediai.enterprise.feature.reports.presentation.timeline.ReportTimelineRoute

fun NavController.navigateToReports(navOptions: NavOptions? = null) {
    this.navigate(MediAINavDestinations.REPORTS_ROUTE, navOptions)
}

fun NavGraphBuilder.reportGraph(
    navController: NavController
) {
    composable(route = MediAINavDestinations.REPORTS_ROUTE) {
        ReportTimelineRoute(
            onNavigateToScan = { navController.navigate("scan_report") },
            onReportClick = { reportId ->
                // Navigate to report detail
            }
        )
    }

    composable(route = "scan_report") {
        ScanScreen(
            onScanComplete = { navController.popBackStack() },
            onBack = { navController.popBackStack() }
        )
    }
}
