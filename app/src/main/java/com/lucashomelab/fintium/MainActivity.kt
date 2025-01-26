package com.lucashomelab.fintium

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lucashomelab.fintium.presentation.navigation.AppNavigation
import com.lucashomelab.fintium.presentation.navigation.BottomNavBar
import com.lucashomelab.fintium.presentation.navigation.Screen
import com.lucashomelab.fintium.ui.theme.FintiumTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FintiumTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                var hasError by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = hasError) {
                    if (hasError) {
                        // Manejar error
                        hasError = false
                    }
                }

                    Scaffold(
                        bottomBar = {
                            // No mostrar la barra inferior en pantallas como AddBudget o AssetDetail
                            if (currentDestination in listOf(
                                    Screen.Dashboard.route,
                                    Screen.Reports.route,
                                    Screen.Budget.route,
                                    Screen.Settings.route,
                                    Screen.Portfolio.route,
                                    Screen.News.route
                                )
                            ) {
                                BottomNavBar(
                                    navController = navController,
                                    currentDestination = currentDestination
                                )
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            AppNavigation(navController)
                        }
                    }
            }
        }
    }
}
