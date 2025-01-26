package com.lucashomelab.fintium.data.remote.dto

import com.lucashomelab.fintium.domain.model.NewsItem
import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.Locale

data class NewsResponseDto(
    @Json(name = "feed")
    val articles: List<NewsArticleDto>,
    @Json(name = "items")
    val totalResults: String,
    @Json(name = "sentiment_score_definition")
    val sentimentDefinition: String,
    @Json(name = "relevance_score_definition")
    val relevanceDefinition: String
)

data class NewsArticleDto(
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "time_published")
    val publishedAt: String,
    @Json(name = "summary")
    val description: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "overall_sentiment_score")
    val sentimentScore: Double,
) {
    fun toDomainModel() = NewsItem(
        id = url, // Usando URL como ID único
        title = title,
        description = description,
        source = source,
        url = url,
        imageUrl = null,
        publishedAt = parseDateTime(publishedAt),
        relatedSymbols = emptyList()
    )
}

private fun parseDateTime(dateTime: String): Long {
    return try {
        SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.US)
            .parse(dateTime)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}
