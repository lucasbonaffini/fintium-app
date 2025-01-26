package com.lucashomelab.fintium.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.data.repository.AssetRepository
import com.lucashomelab.fintium.domain.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardState(
    val assets: List<Asset> = emptyList(),
    val totalPortfolioValue: Double = 0.0,
    val totalProfitLoss: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val assetRepository: AssetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        loadAssets()
        startPriceUpdates()
    }

    private fun loadAssets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                assetRepository.getAllAssets().collect { assets ->
                    _state.update { state ->
                        state.copy(
                            assets = assets,
                            totalPortfolioValue = assets.sumOf { it.totalValue },
                            totalProfitLoss = assets.sumOf { it.profitLoss },
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to load assets: ${e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun startPriceUpdates() {
        viewModelScope.launch {
            while(true) {
                try {
                    println("Starting price update")  // Log
                    updatePrices()
                    println("Price update completed")  // Log
                    delay(30000) // Actualizar cada 30 segundos en lugar de 60
                } catch (e: Exception) {
                    println("Error in price update: ${e.message}")  // Log
                    delay(5000)
                }
            }
        }
    }

    private suspend fun updatePrices() {
        try {
            assetRepository.updateAssetPrices()
            _state.update { it.copy(error = null) }
        } catch (e: Exception) {
            _state.update {
                it.copy(error = "Failed to update prices: ${e.localizedMessage}")
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                loadAssets()
                updatePrices()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Refresh failed: ${e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Cleanup if needed
    }
}

