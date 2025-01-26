package com.lucashomelab.fintium.data.remote.api
import com.lucashomelab.fintium.data.remote.dto.CryptoResponse
import com.lucashomelab.fintium.data.remote.dto.GlobalQuoteResponse
import com.lucashomelab.fintium.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

// FinanceApi.kt
interface FinanceApi {
    companion object {
        const val BASE_URL = "https://www.alphavantage.co/"
        const val API_KEY = "5EW5L7XPFXCEZIDD"
    }

    @GET("query")
    suspend fun getAssetPrice(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): GlobalQuoteResponse

    @GET("query")
    suspend fun getCryptoPrice(
        @Query("function") function: String = "CURRENCY_EXCHANGE_RATE",
        @Query("from_currency") symbol: String,
        @Query("to_currency") toCurrency: String = "USD",
        @Query("apikey") apiKey: String = API_KEY
    ): CryptoResponse

    @GET("query")
    suspend fun getFinancialNews(
        @Query("function") function: String = "NEWS_SENTIMENT",
        @Query("topics") topics: String = "financial_markets",
        @Query("limit") limit: Int = 20,
        @Query("sort") sort: String = "LATEST",
        @Query("apikey") apiKey: String = API_KEY
    ): NewsResponseDto
}
