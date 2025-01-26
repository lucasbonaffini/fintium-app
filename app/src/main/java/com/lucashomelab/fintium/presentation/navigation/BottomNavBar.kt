package com.lucashomelab.fintium.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentDestination: String?
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination == Screen.Dashboard.route,
            onClick = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentDestination == Screen.Reports.route,
            onClick = {
                navController.navigate(Screen.Reports.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Assessment, contentDescription = "Reports") },
            label = { Text("Reports") }
        )

        NavigationBarItem(
            selected = currentDestination == Screen.Budget.route,
            onClick = {
                navController.navigate(Screen.Budget.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Budget") },
            label = { Text("Budget") }
        )

        NavigationBarItem(
            selected = currentDestination == Screen.Portfolio.route,
            onClick = {
                navController.navigate(Screen.Portfolio.route) {
                    popUpTo(Screen.Dashboard.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Portfolio") },
            label = { Text("Portfolio") }
        )
        NavigationBarItem(
            selected = currentDestination == Screen.News.route,
            onClick = {
                navController.navigate(Screen.News.route) {
                    popUpTo(Screen.Dashboard.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Article, contentDescription = "News") },
            label = { Text("News") }
        )

        NavigationBarItem(
            selected = currentDestination == Screen.Settings.route,
            onClick = {
                navController.navigate(Screen.Settings.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            label = { Text("Account") }
        )
    }
}
