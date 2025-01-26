package com.lucashomelab.fintium.presentation.assetdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.data.repository.AssetRepository
import com.lucashomelab.fintium.domain.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssetDetailState(
    val asset: Asset? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AssetDetailViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AssetDetailState())
    val state: StateFlow<AssetDetailState> = _state

    init {
        savedStateHandle.get<String>("assetId")?.let { assetId ->
            loadAsset(assetId)
        }
    }

    private fun loadAsset(assetId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val asset = assetRepository.getAssetById(assetId)
                _state.update {
                    it.copy(
                        asset = asset,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Failed to load asset",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun deleteAsset() {
        viewModelScope.launch {
            _state.value.asset?.let { asset ->
                try {
                    assetRepository.deleteAsset(asset)
                } catch (e: Exception) {
                    _state.update {
                        it.copy(error = e.message ?: "Failed to delete asset")
                    }
                }
            }
        }
    }
}
