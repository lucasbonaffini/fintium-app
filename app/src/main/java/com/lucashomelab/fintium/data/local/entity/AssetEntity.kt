package com.lucashomelab.fintium.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lucashomelab.fintium.domain.model.Asset
import com.lucashomelab.fintium.domain.model.AssetType

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val type: AssetType,
    val currentPrice: Double,
    val quantity: Double,
    val purchasePrice: Double,
    val lastUpdated: Long
) {
    fun toAsset(): Asset = Asset(
        id = id,
        symbol = symbol,
        name = name,
        type = type,
        currentPrice = currentPrice,
        quantity = quantity,
        purchasePrice = purchasePrice,
        lastUpdated = lastUpdated
    )

    companion object {
        fun fromAsset(asset: Asset) = AssetEntity(
            id = asset.id,
            symbol = asset.symbol,
            name = asset.name,
            type = asset.type,
            currentPrice = asset.currentPrice,
            quantity = asset.quantity,
            purchasePrice = asset.purchasePrice,
            lastUpdated = asset.lastUpdated
        )
    }
}
