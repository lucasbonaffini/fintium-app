package com.lucashomelab.fintium.presentation.addasset

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
import java.util.UUID
import javax.inject.Inject

data class AddAssetState(
    val symbol: String = "",
    val name: String = "",
    val type: AssetType = AssetType.STOCK,
    val quantity: String = "",
    val purchasePrice: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    private val assetRepository: AssetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddAssetState())
    val state: StateFlow<AddAssetState> = _state

    fun onSymbolChange(symbol: String) {
        _state.update { it.copy(symbol = symbol) }
    }

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun onTypeChange(type: AssetType) {
        _state.update { it.copy(type = type) }
    }

    fun onQuantityChange(quantity: String) {
        _state.update { it.copy(quantity = quantity) }
    }

    fun onPurchasePriceChange(price: String) {
        _state.update { it.copy(purchasePrice = price) }
    }

    fun addAsset() {
        viewModelScope.launch {
            try {
                // Input validation
                if (_state.value.symbol.isBlank()) {
                    throw IllegalArgumentException("Symbol cannot be empty")
                }
                if (_state.value.name.isBlank()) {
                    throw IllegalArgumentException("Name cannot be empty")
                }

                val quantity = _state.value.quantity.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid quantity")
                if (quantity <= 0) {
                    throw IllegalArgumentException("Quantity must be greater than 0")
                }

                val purchasePrice = _state.value.purchasePrice.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid purchase price")
                if (purchasePrice <= 0) {
                    throw IllegalArgumentException("Purchase price must be greater than 0")
                }

                _state.update { it.copy(isLoading = true, error = null) }

                // Create new asset
                val newAsset = Asset(
                    id = UUID.randomUUID().toString(),
                    symbol = _state.value.symbol.uppercase(),
                    name = _state.value.name,
                    type = _state.value.type,
                    currentPrice = purchasePrice, // Initially set to purchase price
                    quantity = quantity,
                    purchasePrice = purchasePrice,
                    lastUpdated = System.currentTimeMillis()
                )

                // Add to repository
                assetRepository.addAsset(newAsset)

                // Update state to indicate success
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        error = null
                    )
                }

            } catch (e: IllegalArgumentException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message,
                        isSuccess = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to add asset: ${e.message}",
                        isSuccess = false
                    )
                }
            }
        }
    }

    fun resetState() {
        _state.update {
            AddAssetState()
        }
    }
}
