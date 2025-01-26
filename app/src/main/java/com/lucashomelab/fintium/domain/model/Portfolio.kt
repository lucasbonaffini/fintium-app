package com.lucashomelab.fintium.domain.model

data class Portfolio (
    val id: String,
    val name: String,
    val assets: List<Asset>,
    val createdAt: Long,
    val lastUpdated: Long
) {
    val totalValue: Double get() = assets.sumOf { it.totalValue }
    private val totalProfitLoss: Double get() = assets.sumOf { it.profitLoss }
    val totalProfitLossPercentage: Double get() =
        if (assets.isEmpty()) 0.0
        else (totalProfitLoss / assets.sumOf { it.purchasePrice * it.quantity }) * 100
}


