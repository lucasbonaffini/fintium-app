package com.lucashomelab.fintium.presentation.addasset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lucashomelab.fintium.domain.model.AssetType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetScreen(
    onAssetAdded: () -> Unit,
    viewModel: AddAssetViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onAssetAdded()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Asset") },
                navigationIcon = {
                    IconButton(onClick = onAssetAdded) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Asset Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AssetsTypeChips(
                    selectedType = state.type,
                    onTypeSelected = viewModel::onTypeChange
                )
            }

            // Symbol Input
            OutlinedTextField(
                value = state.symbol,
                onValueChange = viewModel::onSymbolChange,
                label = { Text("Symbol") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Name Input
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Quantity Input
            OutlinedTextField(
                value = state.quantity,
                onValueChange = viewModel::onQuantityChange,
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Purchase Price Input
            OutlinedTextField(
                value = state.purchasePrice,
                onValueChange = viewModel::onPurchasePriceChange,
                label = { Text("Purchase Price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // Add Button
            Button(
                onClick = viewModel::addAsset,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Add Asset")
                }
            }

            // Error Message
            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun AssetsTypeChips(
    selectedType: AssetType,
    onTypeSelected: (AssetType) -> Unit
) {
    AssetType.values().forEach { type ->
        FilterChip(
            selected = type == selectedType,
            onClick = { onTypeSelected(type) },
            label = { Text(type.name.lowercase().capitalize()) }
        )
    }
}
