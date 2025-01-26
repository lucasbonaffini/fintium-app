package com.lucashomelab.fintium.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Portfolio : Screen("portfolio")
    object Budget : Screen("budget")
    object News : Screen("news")
    object Reports : Screen("reports")
    object Settings : Screen("settings")
    object AddAsset : Screen("add_asset")
    object AddBudget : Screen("add_budget")
    object AssetDetail : Screen("asset_detail/{assetId}") {
        fun createRoute(assetId: String) = "asset_detail/$assetId"
    }
}
