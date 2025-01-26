package com.lucashomelab.fintium.presentation.dashboard

import com.lucashomelab.fintium.ui.theme.BackgroundGradient
import com.lucashomelab.fintium.ui.theme.CircularGlassCard
import com.lucashomelab.fintium.ui.theme.GlassCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lucashomelab.fintium.presentation.components.AssetCard

@Composable
fun DashboardScreen(
    onAssetClick: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    BackgroundGradient {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Bar
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black.copy(alpha = 0.7f)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.Black.copy(alpha = 0.7f)
                            )
                            Icon(
                                imageVector = Icons.Default.ChatBubble,
                                contentDescription = "Messages",
                                tint = Color.Black.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                // Balance Card
                item {
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Available Balance",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.Black.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Text(
                                    text = "Details >",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                val formattedValue = String.format("%.2f", state.totalPortfolioValue)
                                val (integerPart, decimalPart) = formattedValue.split(".")

                                Text(
                                    text = "$$integerPart",
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = ".$decimalPart",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal
                                    ),
                                    modifier = Modifier.padding(bottom = 8.dp, start = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "24 more days - ",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                                Text(
                                    text = "$34.78 per day.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                                Text(
                                    text = " Lest",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            }

                            Text(
                                text = "$510.79 of $1200",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                }

                // Goal Card
                item {
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CircularGlassCard(
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AccountBalance,
                                                contentDescription = null,
                                                tint = Color.Black.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Summer vacation",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.Black.copy(alpha = 0.8f)
                                        )
                                    )
                                }
                                Text(
                                    "Goal",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            LinearProgressIndicator(
                                progress = 0.55f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Jan 2025",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                                Text(
                                    "Mar 2025",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            }
                        }
                    }
                }

                // Expenses Section
                item {
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Text(
                                text = "Expenses",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$1,472.74",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = "2.5% ↑",
                                    color = Color(0xFF4CAF50)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(vertical = 16.dp)
                            )
                        }
                    }
                }

                // Assets Section Header
                if (!state.isLoading && state.assets.isNotEmpty()) {
                    item {
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Your Assets",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.Black.copy(alpha = 0.8f)
                                    )
                                )
                                IconButton(onClick = { /* View all */ }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "View all",
                                        tint = Color.Black.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    // Assets List
                    items(state.assets.size) { index ->
                        AssetCard(
                            asset = state.assets[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAssetClick(state.assets[index].id)
                                }
                        )
                    }
                }
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Error Snackbar
            state.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(error)
                }
            }
        }
    }
}
