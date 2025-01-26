package com.lucashomelab.fintium.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucashomelab.fintium.domain.model.NewsItem
import com.lucashomelab.fintium.data.remote.api.FinanceApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

data class NewsState(
    val news: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val isLastPage: Boolean = false
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val financeApi: FinanceApi
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state

    init {
        loadNews()
    }

    fun loadNews(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }

                if (forceRefresh) {
                    _state.update { it.copy(news = emptyList(), currentPage = 1, isLastPage = false) }
                }

                if (_state.value.isLastPage) return@launch

                val response = financeApi.getFinancialNews(
                    topics = "financial_markets",
                    limit = 20
                )

                _state.update { currentState ->
                    currentState.copy(
                        news = if (forceRefresh) {
                            response.articles.map { it.toDomainModel() }
                        } else {
                            currentState.news + response.articles.map { it.toDomainModel() }
                        },
                        currentPage = currentState.currentPage + 1,
                        isLastPage = response.articles.isEmpty(),
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to load news: ${e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }
}
