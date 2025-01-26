package com.lucashomelab.fintium.domain.model

import androidx.room.PrimaryKey

data class Asset (
    val id: String,
    val symbol: String,
    val name: String,
    val type: AssetType,
    val currentPrice: Double,
    val quantity: Double,
    val purchasePrice: Double,
    val lastUpdated: Long

) {
    val totalValue: Double get() = currentPrice * quantity
    val profitLoss: Double get() = (currentPrice - purchasePrice) * quantity
    val profitLossPercentage: Double get() = ((currentPrice - purchasePrice) / purchasePrice) * 100
}
