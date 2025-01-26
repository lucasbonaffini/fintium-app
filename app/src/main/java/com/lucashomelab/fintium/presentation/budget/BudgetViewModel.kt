package com.lucashomelab.fintium.presentation.budget

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.domain.model.BudgetCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor() : ViewModel() {

    data class BudgetState(
        val categories: List<BudgetCategory> = emptyList(),
        val totalBudget: Double = 0.0,
        val totalSpent: Double = 0.0,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(BudgetState())
    val state: StateFlow<BudgetState> = _state

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                // Aquí cargarías las categorías reales desde tu repositorio
                val dummyCategories = listOf(
                    BudgetCategory(
                        id = "1",
                        name = "Home",
                        icon = "🏠",
                        color = Color(0xFFFF4B8C),
                        budget = 1000.0,
                        spent = 350.0,
                        transactions = 74
                    ),
                    BudgetCategory(
                        id = "2",
                        name = "Shopping",
                        icon = "🛍️",
                        color = Color(0xFF6C63FF),
                        budget = 500.0,
                        spent = 220.0,
                        transactions = 74
                    )
                    // Agrega más categorías según necesites
                )

                _state.update {
                    it.copy(
                        categories = dummyCategories,
                        totalBudget = dummyCategories.sumOf { it.budget },
                        totalSpent = dummyCategories.sumOf { it.spent },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to load categories: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addCategory(category: BudgetCategory) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    categories = it.categories + category
                )
            }
        }
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    categories = it.categories.filter { it.id != categoryId }
                )
            }
        }
    }
}
