package com.lucashomelab.fintium.presentation.portfolio


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lucashomelab.fintium.domain.model.AssetType
import com.lucashomelab.fintium.presentation.components.AssetCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    onAddAssetClick: () -> Unit,
    onAssetClick: (String) -> Unit,
    viewModel: PortfolioViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portfolio") },
                actions = {
                    IconButton(onClick = onAddAssetClick) {
                        Icon(Icons.Default.Add, "Add Asset")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TabRow(
                    selectedTabIndex = if (state.selectedType == AssetType.STOCK) 0 else 1
                ) {
                    Tab(
                        selected = state.selectedType == AssetType.STOCK,
                        onClick = { viewModel.onAssetTypeSelected(AssetType.STOCK) },
                        text = { Text("Stocks") }
                    )
                    Tab(
                        selected = state.selectedType == AssetType.CRYPTOCURRENCY,
                        onClick = { viewModel.onAssetTypeSelected(AssetType.CRYPTOCURRENCY) },
                        text = { Text("Crypto") }
                    )
                }

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    val assets = if (state.selectedType == AssetType.STOCK) {
                        state.stocks
                    } else {
                        state.crypto
                    }

                    if (assets.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No ${state.selectedType.name.lowercase()} assets found",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(assets.size) { index ->
                                AssetCard(
                                    asset = assets[index],
                                    modifier = Modifier.clickable {
                                        onAssetClick(assets[index].id)
                                    }
                                )
                            }
                        }
                    }
                }
            }

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
