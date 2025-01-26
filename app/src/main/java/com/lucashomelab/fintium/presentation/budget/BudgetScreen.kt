package com.lucashomelab.fintium.presentation.budget

import androidx.compose.foundation.Image
import com.lucashomelab.fintium.ui.theme.BackgroundGradient
import com.lucashomelab.fintium.ui.theme.GlassCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lucashomelab.fintium.R
import com.lucashomelab.fintium.presentation.budget.components.CategoryCard

@Composable
fun BudgetScreen(
    onAddClick: () -> Unit,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    BackgroundGradient {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Budget Categories",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onAddClick) {
                    Icon(Icons.Default.Add, contentDescription = "Add Category")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Categories
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Add Budget Category",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
//                            Image(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(120.dp),
//                                contentScale = ContentScale.Fit,
//                                painter = painterResource(id = R.drawable.budget_illustration),
//                                contentDescription = null
//                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onAddClick,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Text("Add New Categories")
                            }
                        }
                    }
                }

                items(state.categories) { category ->
                    CategoryCard(
                        category = category,
                        onDeleteClick = { viewModel.deleteCategory(category.id) }
                    )
                }
            }
        }
    }
}


