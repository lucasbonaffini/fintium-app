package com.lucashomelab.fintium.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lucashomelab.fintium.presentation.dashboard.DashboardScreen
import com.lucashomelab.fintium.presentation.portfolio.PortfolioScreen
import com.lucashomelab.fintium.presentation.addasset.AddAssetScreen
import com.lucashomelab.fintium.presentation.assetdetail.AssetDetailScreen
import com.lucashomelab.fintium.presentation.addbudget.AddBudgetScreen
import com.lucashomelab.fintium.presentation.budget.BudgetScreen
import com.lucashomelab.fintium.presentation.news.NewsScreen
import com.lucashomelab.fintium.presentation.reports.ReportsScreen
import com.lucashomelab.fintium.presentation.settings.SettingsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onAssetClick = { assetId ->
                    navController.navigate(Screen.AssetDetail.createRoute(assetId))
                }
            )
        }

        composable(Screen.Portfolio.route) {
            PortfolioScreen(
                onAddAssetClick = {
                    navController.navigate(Screen.AddAsset.route)
                },
                onAssetClick = { assetId ->
                    navController.navigate(Screen.AssetDetail.createRoute(assetId))
                }
            )
        }

        composable(Screen.Budget.route) {
            BudgetScreen(
                onAddClick = {
                    navController.navigate(Screen.AddBudget.route)
                }
            )
        }
        composable(Screen.AddBudget.route) {
            AddBudgetScreen(
                onDismiss = { navController.popBackStack() },
                onSave = {
                    // Save logic
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.AddAsset.route) {
            AddAssetScreen(
                onAssetAdded = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.AssetDetail.route,
            arguments = listOf(
                navArgument("assetId") { type = NavType.StringType }
            )
        ) {
            AssetDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }




        composable(Screen.News.route) {
            NewsScreen()
        }

        composable(Screen.Reports.route) {
            ReportsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
