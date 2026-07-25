package com.mediai.enterprise.feature.reports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mediai.enterprise.core.navigation.MediAINavDestinations
import com.mediai.enterprise.feature.reports.presentation.detail.ReportDetailRoute
import com.mediai.enterprise.feature.reports.presentation.scan.ScanScreen
import com.mediai.enterprise.feature.reports.presentation.timeline.ReportTimelineRoute

fun NavController.navigateToReports(navOptions: NavOptions? = null) {
    this.navigate(MediAINavDestinations.REPORTS_ROUTE, navOptions)
}

fun NavController.navigateToReportDetail(reportId: String, navOptions: NavOptions? = null) {
    this.navigate("${MediAINavDestinations.REPORT_DETAIL_ROUTE}/$reportId", navOptions)
}

fun NavGraphBuilder.reportGraph(
    navController: NavController
) {
    composable(route = MediAINavDestinations.REPORTS_ROUTE) {
        ReportTimelineRoute(
            onNavigateToScan = { navController.navigate("scan_report") },
            onReportClick = { reportId ->
                navController.navigateToReportDetail(reportId)
            }
        )
    }

    composable(route = "scan_report") {
        ScanScreen(
            onScanComplete = { navController.popBackStack() },
            onBack = { navController.popBackStack() }
        )
    }

    composable(
        route = "${MediAINavDestinations.REPORT_DETAIL_ROUTE}/{reportId}",
        arguments = listOf(navArgument("reportId") { type = NavType.StringType })
    ) { backStackEntry ->
        val reportId = backStackEntry.arguments?.getString("reportId") ?: return@composable
        ReportDetailRoute(
            reportId = reportId,
            onBack = { navController.popBackStack() }
        )
    }
}
