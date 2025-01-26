package com.lucashomelab.fintium.presentation.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.data.repository.AssetRepository
import com.lucashomelab.fintium.domain.model.Asset
import com.lucashomelab.fintium.domain.model.AssetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PortfolioState(
    val stocks: List<Asset> = emptyList(),
    val crypto: List<Asset> = emptyList(),
    val selectedType: AssetType = AssetType.STOCK,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val assetRepository: AssetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PortfolioState())
    val state: StateFlow<PortfolioState> = _state

    init {
        loadAssets()
    }

    private fun loadAssets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                assetRepository.getAllAssets().collect { assets ->
                    _state.update { state ->
                        state.copy(
                            stocks = assets.filter { it.type == AssetType.STOCK },
                            crypto = assets.filter { it.type == AssetType.CRYPTOCURRENCY },
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Failed to load assets",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAssetTypeSelected(type: AssetType) {
        _state.update { it.copy(selectedType = type) }
    }

    fun refresh() {
        loadAssets()
    }
}
