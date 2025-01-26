package com.lucashomelab.fintium.data.remote.dto


import com.squareup.moshi.Json

data class GlobalQuoteResponse(
    @Json(name = "Global Quote")
    val quote: AssetPriceDto?
)

data class AssetPriceDto(
    @Json(name = "01. symbol") val symbol: String,
    @Json(name = "02. open") val open: String,
    @Json(name = "03. high") val high: String,
    @Json(name = "04. low") val low: String,
    @Json(name = "05. price") val price: String,
    @Json(name = "06. volume") val volume: String,
    @Json(name = "07. latest trading day") val latestTradingDay: String,
    @Json(name = "08. previous close") val previousClose: String,
    @Json(name = "09. change") val change: String,
    @Json(name = "10. change percent") val changePercent: String
)

data class CryptoResponse(
    @Json(name = "Realtime Currency Exchange Rate")
    val exchangeRate: CryptoExchangeRateDto
)

data class CryptoExchangeRateDto(
    @Json(name = "1. From_Currency Code") val fromCurrency: String,
    @Json(name = "5. Exchange Rate") val exchangeRate: String,
    @Json(name = "6. Last Refreshed") val lastUpdated: String
)
