package com.lucashomelab.fintium.domain.model

data class NewsItem (
    val id: String,
    val title: String,
    val description: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val relatedSymbols: List<String>
)
