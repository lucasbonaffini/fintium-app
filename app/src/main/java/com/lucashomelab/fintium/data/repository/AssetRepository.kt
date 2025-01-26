package com.lucashomelab.fintium.data.repository

import com.lucashomelab.fintium.domain.model.Asset
import com.lucashomelab.fintium.domain.model.AssetType
import kotlinx.coroutines.flow.Flow

interface AssetRepository {
    fun getAllAssets(): Flow<List<Asset>>
    fun getAssetsByType(type: AssetType): Flow<List<Asset>>
    suspend fun getAssetById(id: String): Asset?
    suspend fun addAsset(asset: Asset)
    suspend fun deleteAsset(asset: Asset)
    suspend fun updateAssetPrices()
}

