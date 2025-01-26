package com.lucashomelab.fintium.presentation.addbudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBudgetViewModel @Inject constructor() : ViewModel() {

    data class AddBudgetState(
        val selectedCategory: String = "",
        val amount: String = "",
        val note: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false
    )

    private val _state = MutableStateFlow(AddBudgetState())
    val state: StateFlow<AddBudgetState> = _state

    fun onCategoryChange(category: String) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun onAmountChange(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun onNoteChange(note: String) {
        _state.update { it.copy(note = note) }
    }

    fun saveBudget() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                val amount = _state.value.amount.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid amount")

                if (_state.value.selectedCategory.isBlank()) {
                    throw IllegalArgumentException("Category must be selected")
                }

                // Aquí iría la lógica para guardar en el repositorio

                _state.update {
                    it.copy(
                        isSuccess = true,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message ?: "Failed to save budget",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearForm() {
        _state.update {
            AddBudgetState()
        }
    }
}
