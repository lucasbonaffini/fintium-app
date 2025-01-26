package com.lucashomelab.fintium.presentation.reports

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.data.repository.AssetRepository
import com.lucashomelab.fintium.domain.model.BudgetCategory
import com.lucashomelab.fintium.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val assetRepository: AssetRepository
) : ViewModel() {

    data class ReportsState(
        val totalNetWorth: Double = 0.0,
        val monthlyChange: Double = 0.0,
        val highestExpense: Double = 0.0,
        val lowestExpense: Double = 0.0,
        val monthlyIncome: Double = 0.0,
        val businessIncome: Double = 0.0,
        val timeRange: TimeRange = TimeRange.MONTH,
        val chartData: List<Expense> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    enum class TimeRange {
        WEEK, MONTH, YEAR
    }

    private val _state = MutableStateFlow(ReportsState())
    val state: StateFlow<ReportsState> = _state

    init {
        updateChartData(TimeRange.MONTH)
    }

    private fun updateChartData(timeRange: TimeRange) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val endDate = LocalDate.now()
                val startDate = when (timeRange) {
                    TimeRange.WEEK -> endDate.minusWeeks(1)
                    TimeRange.MONTH -> endDate.minusMonths(1)
                    TimeRange.YEAR -> endDate.minusYears(1)
                }

                val chartData = generateDummyData(startDate, endDate)

                _state.update { currentState ->
                    currentState.copy(
                        timeRange = timeRange,
                        chartData = chartData,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to update chart data: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun generateDummyData(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Expense> {
        val data = mutableListOf<Expense>()
        var currentDate = startDate

        while (!currentDate.isAfter(endDate)) {
            val randomCategory = dummyCategories.random()
            val randomAmount = (100..1000).random().toDouble()

            data.add(
                Expense(
                    id = UUID.randomUUID().toString(),
                    title = "Expense for ${randomCategory.name}",
                    amount = randomAmount,
                    category = randomCategory.copy(
                        spent = randomCategory.spent + randomAmount,
                        transactions = randomCategory.transactions + 1
                    ),
                    date = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    note = null
                )
            )
            currentDate = currentDate.plusDays(1)
        }

        return data
    }

    fun onTimeRangeSelected(timeRange: TimeRange) {
        updateChartData(timeRange)
    }

    private val dummyCategories = listOf(
        BudgetCategory(
            id = "1",
            name = "Food",
            icon = "🍕",
            color = Color(0xFFFF4B8C),
            budget = 500.0,
            spent = 0.0,
            transactions = 0
        ),
        BudgetCategory(
            id = "2",
            name = "Transport",
            icon = "🚗",
            color = Color(0xFF6C63FF),
            budget = 300.0,
            spent = 0.0,
            transactions = 0
        ),
        BudgetCategory(
            id = "3",
            name = "Entertainment",
            icon = "🎮",
            color = Color(0xFF4CAF50),
            budget = 200.0,
            spent = 0.0,
            transactions = 0
        ),
        BudgetCategory(
            id = "4",
            name = "Shopping",
            icon = "🛍️",
            color = Color(0xFFFFA726),
            budget = 400.0,
            spent = 0.0,
            transactions = 0
        )
    )
}
